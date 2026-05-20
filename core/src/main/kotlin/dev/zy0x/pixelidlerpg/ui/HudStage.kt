package dev.zy0x.pixelidlerpg.ui

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import dev.zy0x.pixelidlerpg.assets.GameAssetManager
import dev.zy0x.pixelidlerpg.rendering.SpriteRenderer
import dev.zy0x.pixelidlerpg.ui.widgets.*
import ktx.actors.plusAssign
import ktx.log.logger
import ktx.scene2d.*

/**
 * HudStage — the entire in-game UI as a Scene2D [Stage].
 *
 * Shares the same [FitViewport] dimensions as [SpriteRenderer] (360x640).
 * Rendered LAST each frame, always on top of sprites and tilemap.
 *
 * Layout (portrait, virtual 360x640):
 * ┌──────────────────────────────────────┐
 * │  [GoldIcon] 1,234 gold     [Wave 5/10]  │  ← top bar (y=600)
 * │                                         │
 * │       [Enemy HP bar + name]             │  ← enemy panel (y=520)
 * │                                         │
 * │      [tilemap + sprites area]           │
 * │                                         │
 * │  [Hero 1 HP]  [Hero 2 HP]  [Hero 3 HP]  │  ← hero panels (y=140)
 * │                                         │
 * │  [Ability 1]  [Ability 2]  [Ability 3]  │  ← ability bar (y=24)
 * └──────────────────────────────────────┘
 */
class HudStage(
    private val assets: GameAssetManager,
    batch: SpriteBatch,
) : Stage(FitViewport(SpriteRenderer.V_WIDTH, SpriteRenderer.V_HEIGHT), batch) {

    companion object {
        private val log = logger<HudStage>()
    }

    // --- Widgets (lateinit, built in buildUi()) ---
    lateinit var goldCounter: GoldCounter
        private set
    lateinit var waveLabel: WaveLabel
        private set
    lateinit var enemyPanel: EnemyPanel
        private set
    val heroPanels: MutableList<HeroPanel> = mutableListOf()
    lateinit var abilityBar: AbilityBar
        private set
    lateinit var combatLog: CombatLog
        private set

    init {
        buildUi()
        log.info { "HudStage built" }
    }

    private fun buildUi() {
        // -- Top bar --
        goldCounter = GoldCounter(assets)
        goldCounter.setPosition(8f, SpriteRenderer.V_HEIGHT - 24f)
        this += goldCounter

        waveLabel = WaveLabel()
        waveLabel.setPosition(SpriteRenderer.V_WIDTH - 100f, SpriteRenderer.V_HEIGHT - 24f)
        this += waveLabel

        // -- Enemy HP panel --
        enemyPanel = EnemyPanel(assets)
        enemyPanel.setPosition(
            SpriteRenderer.V_WIDTH / 2f - EnemyPanel.WIDTH / 2f,
            SpriteRenderer.V_HEIGHT - 80f
        )
        this += enemyPanel

        // -- Combat log (scrolling text, bottom-left) --
        combatLog = CombatLog()
        combatLog.setPosition(4f, 180f)
        this += combatLog

        // -- Ability bar (bottom) --
        abilityBar = AbilityBar(assets, slotCount = 3)
        abilityBar.setPosition(
            SpriteRenderer.V_WIDTH / 2f - AbilityBar.totalWidth(3) / 2f,
            8f
        )
        this += abilityBar
    }

    /**
     * Call after [buildUi] when heroes are known (from SaveGame).
     * Creates one [HeroPanel] per hero slot.
     */
    fun spawnHeroPanels(heroClassKeys: List<String>) {
        heroPanels.clear()
        val totalW = heroClassKeys.size * (HeroPanel.WIDTH + 4f) - 4f
        val startX = SpriteRenderer.V_WIDTH / 2f - totalW / 2f

        heroClassKeys.forEachIndexed { i, classKey ->
            val panel = HeroPanel(assets, classKey)
            panel.setPosition(startX + i * (HeroPanel.WIDTH + 4f), 140f)
            this += panel
            heroPanels += panel
        }
    }

    // ---- Public update API (called from GameScreen event handlers) ----------

    fun updateGold(amount: Long) = goldCounter.setGold(amount)
    fun updateWave(current: Int, total: Int) = waveLabel.setWave(current, total)
    fun updateEnemyHp(current: Int, max: Int, name: String, isBoss: Boolean) =
        enemyPanel.update(current, max, name, isBoss)
    fun updateHeroHp(slot: Int, current: Int, max: Int, isDead: Boolean) =
        heroPanels.getOrNull(slot)?.update(current, max, isDead)
    fun addCombatLine(text: String) = combatLog.addLine(text)
    fun setAbilityCooldown(slot: Int, progress: Float) =
        abilityBar.setCooldown(slot, progress)

    fun resize(width: Int, height: Int) = viewport.update(width, height, true)

    override fun dispose() {
        super.dispose()
        log.info { "HudStage disposed" }
    }
}
