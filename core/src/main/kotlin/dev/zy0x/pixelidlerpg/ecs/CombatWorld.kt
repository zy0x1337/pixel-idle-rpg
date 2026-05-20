package dev.zy0x.pixelidlerpg.ecs

import com.github.quillraven.fleks.World
import dev.zy0x.pixelidlerpg.domain.HeroClass
import dev.zy0x.pixelidlerpg.domain.Zone
import dev.zy0x.pixelidlerpg.ecs.components.CombatComponent
import dev.zy0x.pixelidlerpg.ecs.components.HeroComponent
import dev.zy0x.pixelidlerpg.ecs.systems.*
import dev.zy0x.pixelidlerpg.save.SaveGame
import ktx.log.logger

/**
 * CombatWorld — assembles the Fleks ECS world for the gameplay session.
 *
 * Created by [GameScreen] on enter, disposed on exit.
 * Pass in a [SaveGame] to restore hero state from save.
 */
class CombatWorld(
    saveGame: SaveGame,
    initialZone: Zone = Zone.entries.firstOrNull {
        it.name == saveGame.currentZoneId
    } ?: Zone.VERDANT_FOREST,
    private val onCombatEvent: (CombatEvent) -> Unit = {},
    private val onReward: (RewardEvent) -> Unit = {},
) {
    companion object {
        private val log = logger<CombatWorld>()
    }

    val zoneState = ZoneState(currentZone = initialZone)

    val world: World = World {
        // Register all systems in execution order
        systems {
            add(SpawnSystem(zoneState))
            add(CombatSystem(zoneState, onCombatEvent))
            add(RewardSystem(zoneState, onReward))
            add(LootSystem(zoneState))
            add(HeroRespawnSystem())
        }
    }

    init {
        spawnHeroes(saveGame)
        log.info { "CombatWorld initialized. Zone: ${initialZone.displayName}, Heroes: ${saveGame.heroes.size}" }
    }

    /** Call every frame from GameScreen.render(delta). */
    fun update(delta: Float) = world.update(delta)

    /** Clean up when leaving GameScreen. */
    fun dispose() = world.dispose()

    private fun spawnHeroes(saveGame: SaveGame) {
        if (saveGame.heroes.isEmpty()) {
            // Fresh game: spawn a default Warrior
            spawnHero(HeroClass.WARRIOR, level = 1, exp = 0L)
        } else {
            saveGame.heroes.forEach { saved ->
                val heroClass = HeroClass.entries.firstOrNull { it.name == saved.heroClassId }
                    ?: HeroClass.WARRIOR
                spawnHero(heroClass, saved.level, saved.exp)
            }
        }
    }

    private fun spawnHero(heroClass: HeroClass, level: Int, exp: Long) {
        world.entity {
            it += HeroComponent(
                heroClass = heroClass,
                level = level,
                exp = exp,
            )
            it += CombatComponent(
                attackInterval = 1f / heroClass.baseStats.spd
            )
        }
        log.info { "Spawned hero: ${heroClass.displayName} (Lv.$level)" }
    }
}
