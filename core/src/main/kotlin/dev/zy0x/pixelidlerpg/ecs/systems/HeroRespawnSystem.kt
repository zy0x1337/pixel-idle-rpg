package dev.zy0x.pixelidlerpg.ecs.systems

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import dev.zy0x.pixelidlerpg.ecs.components.HeroComponent
import ktx.log.logger

/**
 * HeroRespawnSystem — revives dead heroes after a short delay.
 *
 * When all heroes are dead, combat halts until at least one is revived.
 * Revive timer is per-hero (configurable per class in future).
 */
class HeroRespawnSystem : IteratingSystem(
    family = com.github.quillraven.fleks.family { all(HeroComponent) }
) {
    companion object {
        private val log = logger<HeroRespawnSystem>()
        private const val RESPAWN_DELAY = 5f // seconds
    }

    // Per-hero respawn timer stored outside ECS for simplicity at this stage
    private val respawnTimers = mutableMapOf<Entity, Float>()

    override fun onTickEntity(entity: Entity) {
        val hero = entity[HeroComponent]
        if (hero.isAlive) {
            respawnTimers.remove(entity)
            return
        }

        val timer = respawnTimers.getOrPut(entity) { 0f } + deltaTime
        respawnTimers[entity] = timer

        if (timer >= RESPAWN_DELAY) {
            hero.currentHp = (hero.totalMaxHp * 0.5f).toInt().coerceAtLeast(1)
            respawnTimers.remove(entity)
            log.info { "${hero.heroClass.displayName} revived at 50% HP" }
        }
    }
}
