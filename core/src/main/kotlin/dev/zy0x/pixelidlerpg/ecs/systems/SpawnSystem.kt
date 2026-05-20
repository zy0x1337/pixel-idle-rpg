package dev.zy0x.pixelidlerpg.ecs.systems

import com.github.quillraven.fleks.AllOf
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World
import dev.zy0x.pixelidlerpg.domain.Stats
import dev.zy0x.pixelidlerpg.domain.Zone
import dev.zy0x.pixelidlerpg.ecs.components.CombatComponent
import dev.zy0x.pixelidlerpg.ecs.components.EnemyComponent
import ktx.log.logger

/**
 * SpawnSystem — creates a new enemy when none is alive in the current zone.
 *
 * Runs every frame, checks if an active enemy exists.
 * If not, spawns the next enemy (or boss every 10 kills).
 */
class SpawnSystem(
    private val zoneState: ZoneState,
) : IteratingSystem(
    family = com.github.quillraven.fleks.family { all(EnemyComponent) }
) {
    companion object {
        private val log = logger<SpawnSystem>()
    }

    override fun onTickEntity(entity: Entity) {
        // handled in onTick (we check family emptiness, not per-entity)
    }

    override fun onTick() {
        val hasLiveEnemy = family.isNotEmpty() &&
            family.any { world.mapper(EnemyComponent)[it].isAlive }

        if (!hasLiveEnemy) {
            spawnNextEnemy()
        }
    }

    private fun spawnNextEnemy() {
        zoneState.killCount++
        val isBoss = zoneState.killCount % 10 == 0

        val enemy = buildEnemy(
            zone = zoneState.currentZone,
            killCount = zoneState.killCount,
            isBoss = isBoss,
        )

        world.entity {
            it += enemy
            it += CombatComponent(attackInterval = if (isBoss) 2.0f else 1.5f)
        }

        log.info { "Spawned ${if (isBoss) "BOSS" else "enemy"}: ${enemy.displayName} (kill #${zoneState.killCount})" }
    }

    private fun buildEnemy(zone: Zone, killCount: Int, isBoss: Boolean): EnemyComponent {
        val powerScale = 1f + killCount * 0.05f
        val bossMultiplier = if (isBoss) 3f else 1f

        return EnemyComponent(
            id = "${zone.name}_${killCount}",
            displayName = if (isBoss) zoneBossName(zone) else zoneEnemyName(zone),
            stats = Stats(
                maxHp = (zone.recommendedPower.coerceAtLeast(20) * powerScale * bossMultiplier).toInt(),
                atk = (5 * powerScale * bossMultiplier).toInt(),
                def = (2 * powerScale).toInt(),
            ),
            goldReward = ((zone.goldMultiplier * 10 * powerScale * bossMultiplier).toInt()),
            expReward  = ((15 * powerScale * bossMultiplier).toInt()),
            isBoss = isBoss,
        )
    }

    private fun zoneEnemyName(zone: Zone) = when (zone) {
        Zone.VERDANT_FOREST -> listOf("Slime", "Forest Wolf", "Giant Bee").random()
        Zone.DARK_CAVES     -> listOf("Cave Bat", "Goblin Scout", "Rock Crab").random()
        Zone.RUINED_CASTLE  -> listOf("Skeleton Archer", "Cursed Knight", "Ghost").random()
        Zone.MAGMA_DEPTHS   -> listOf("Fire Imp", "Lava Golem", "Ember Drake").random()
        Zone.THE_VOID       -> listOf("Void Wraith", "Shadow Fiend", "Null Entity").random()
    }

    private fun zoneBossName(zone: Zone) = when (zone) {
        Zone.VERDANT_FOREST -> "Ancient Treant"
        Zone.DARK_CAVES     -> "Goblin Warchief"
        Zone.RUINED_CASTLE  -> "Undead King"
        Zone.MAGMA_DEPTHS   -> "Volcanic Dragon"
        Zone.THE_VOID       -> "The Null"
    }
}

/** Mutable state for the current zone session, shared between systems. */
data class ZoneState(
    var currentZone: Zone = Zone.VERDANT_FOREST,
    var killCount: Int = 0,
    var totalGoldEarned: Long = 0L,
    var totalExpEarned: Long = 0L,
)
