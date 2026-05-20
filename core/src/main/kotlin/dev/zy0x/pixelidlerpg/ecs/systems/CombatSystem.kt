package dev.zy0x.pixelidlerpg.ecs.systems

import com.github.quillraven.fleks.AllOf
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import dev.zy0x.pixelidlerpg.ecs.components.CombatComponent
import dev.zy0x.pixelidlerpg.ecs.components.EnemyComponent
import dev.zy0x.pixelidlerpg.ecs.components.HeroComponent
import ktx.log.logger
import kotlin.math.max

/**
 * CombatSystem — drives auto-combat tick between all heroes and the active enemy.
 *
 * Each hero has its own [CombatComponent] attack timer.
 * On attack: hero deals damage to the current enemy.
 * Enemy counterattacks at its own interval (handled via EnemyComponent + CombatComponent).
 */
@AllOf([HeroComponent::class, CombatComponent::class])
class CombatSystem(
    private val zoneState: ZoneState,
    private val onCombatEvent: (CombatEvent) -> Unit = {},
) : IteratingSystem() {

    companion object {
        private val log = logger<CombatSystem>()
    }

    override fun onTickEntity(entity: Entity) {
        val hero   = entity[HeroComponent]
        val combat = entity[CombatComponent]

        if (!hero.isAlive) return

        // Find the first alive enemy
        val enemyEntity = world.family { all(EnemyComponent) }
            .firstOrNull { world.mapper(EnemyComponent)[it].isAlive }
            ?: return // no enemy yet, SpawnSystem will handle it

        val enemy = world.mapper(EnemyComponent)[enemyEntity]

        // Hero attacks on timer
        val heroReady = combat.tickTimer(deltaTime)
        if (heroReady) {
            val dmg = max(1, hero.totalAtk - enemy.stats.def)
            enemy.currentHp = max(0, enemy.currentHp - dmg)

            onCombatEvent(CombatEvent.HeroAttacked(heroClass = hero.heroClass.displayName, damage = dmg))
            log.debug { "${hero.heroClass.displayName} hits ${enemy.displayName} for $dmg dmg (HP: ${enemy.currentHp}/${enemy.stats.maxHp})" }

            if (!enemy.isAlive) {
                onCombatEvent(CombatEvent.EnemyDied(enemy = enemy, isBoss = enemy.isBoss))
                log.info { "${enemy.displayName} defeated! +${enemy.goldReward} gold, +${enemy.expReward} exp" }
            }
        }

        // Enemy counterattacks hero (simplified: hits first alive hero only)
        val enemyCombat = world.mapper(CombatComponent)[enemyEntity]
        val enemyReady = enemyCombat.tickTimer(deltaTime)
        if (enemyReady && enemy.isAlive) {
            val dmg = max(1, enemy.stats.atk - hero.totalDef)
            hero.currentHp = max(0, hero.currentHp - dmg)

            onCombatEvent(CombatEvent.EnemyAttacked(damage = dmg, targetClass = hero.heroClass.displayName))

            if (!hero.isAlive) {
                onCombatEvent(CombatEvent.HeroDied(heroClass = hero.heroClass.displayName))
                log.info { "${hero.heroClass.displayName} has been defeated!" }
            }
        }
    }
}

/** Sealed event hierarchy emitted by CombatSystem for UI + reward handling. */
sealed class CombatEvent {
    data class HeroAttacked(val heroClass: String, val damage: Int) : CombatEvent()
    data class EnemyAttacked(val damage: Int, val targetClass: String) : CombatEvent()
    data class EnemyDied(val enemy: EnemyComponent, val isBoss: Boolean) : CombatEvent()
    data class HeroDied(val heroClass: String) : CombatEvent()
}
