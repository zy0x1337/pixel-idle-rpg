package dev.zy0x.pixelidlerpg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import dev.zy0x.pixelidlerpg.PixelIdleRpgGame
import dev.zy0x.pixelidlerpg.ecs.CombatWorld
import dev.zy0x.pixelidlerpg.ecs.systems.CombatEvent
import dev.zy0x.pixelidlerpg.ecs.systems.RewardEvent
import dev.zy0x.pixelidlerpg.save.SaveManager
import ktx.app.KtxScreen
import ktx.log.logger

/**
 * Core gameplay screen.
 *
 * Owns the [CombatWorld] (Fleks ECS). Delegates rendering to TODO SpriteBatch
 * and HUD to TODO Scene2D stage. Combat + reward events are received via lambdas
 * and will drive HUD updates.
 */
class GameScreen(private val game: PixelIdleRpgGame) : KtxScreen {

    companion object {
        private val log = logger<GameScreen>()
    }

    private lateinit var combatWorld: CombatWorld

    // Accumulated state for HUD (will bind to Scene2D labels later)
    var gold: Long = 0L
        private set
    private val combatLog = ArrayDeque<String>(maxSize = 20)

    override fun show() {
        log.info { "GameScreen shown" }

        val saveGame = SaveManager.load()
        gold = saveGame.gold

        // Check offline rewards
        val offline = SaveManager.calculateOfflineRewards(saveGame, System.currentTimeMillis())
        if (offline.goldEarned > 0) {
            gold += offline.goldEarned
            log.info { "Offline rewards: +${offline.goldEarned} gold for ${offline.secondsAway}s away" }
            // TODO: show WelcomeBackOverlay with offline.goldEarned
        }

        combatWorld = CombatWorld(
            saveGame = saveGame,
            onCombatEvent = ::handleCombatEvent,
            onReward = ::handleRewardEvent,
        )

        // TODO: initialize SpriteBatch, Viewport, HUD stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.08f, 0.1f, 0.12f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Tick the entire ECS world
        combatWorld.update(delta)

        // TODO: SpriteBatch — render tilemap, hero sprites, enemy sprites
        // TODO: HUD Stage — HP bars, gold counter, ability buttons
    }

    override fun resize(width: Int, height: Int) {
        // TODO: viewport.update(width, height, true)
        // TODO: hudStage.viewport.update(width, height, true)
    }

    override fun hide() {
        // Auto-save when leaving gameplay
        persistSave()
    }

    override fun dispose() {
        combatWorld.dispose()
        log.info { "GameScreen disposed" }
        // TODO: spriteBatch.dispose(), hudStage.dispose()
    }

    // -------------------------------------------------------------------------
    // Event Handlers
    // -------------------------------------------------------------------------

    private fun handleCombatEvent(event: CombatEvent) {
        when (event) {
            is CombatEvent.HeroAttacked -> combatLog.addLast("${event.heroClass} hit for ${event.damage}")
            is CombatEvent.EnemyAttacked -> combatLog.addLast("Enemy hit for ${event.damage}")
            is CombatEvent.EnemyDied -> {
                val tag = if (event.isBoss) "[BOSS DEFEATED]" else "[Kill]"
                combatLog.addLast("$tag ${event.enemy.displayName}")
                // TODO: play death animation, show loot pop-up
            }
            is CombatEvent.HeroDied -> {
                combatLog.addLast("${event.heroClass} was defeated! Reviving in 5s...")
                // TODO: show hero KO animation
            }
        }
        if (combatLog.size > 20) combatLog.removeFirst()
    }

    private fun handleRewardEvent(event: RewardEvent) {
        when (event) {
            is RewardEvent.GoldEarned -> {
                gold += event.amount
                // TODO: animate gold counter +amount
            }
            is RewardEvent.HeroLevelUp -> {
                combatLog.addLast("⭐ ${event.heroClass} reached Lv.${event.newLevel}!")
                // TODO: level-up VFX, fanfare sound
            }
        }
    }

    private fun persistSave() {
        val current = SaveManager.load()
        SaveManager.save(
            current.copy(
                gold = gold,
                lastPlayedTimestamp = System.currentTimeMillis(),
                currentZoneId = combatWorld.zoneState.currentZone.name,
            )
        )
    }
}
