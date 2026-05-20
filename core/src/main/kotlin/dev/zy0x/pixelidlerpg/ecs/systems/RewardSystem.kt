package dev.zy0x.pixelidlerpg.ecs.systems

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import dev.zy0x.pixelidlerpg.ecs.components.EnemyComponent
import dev.zy0x.pixelidlerpg.ecs.components.HeroComponent
import ktx.log.logger

/**
 * RewardSystem — distributes gold + EXP from dead enemies to the hero party.
 *
 * Runs after CombatSystem. Finds dead enemies, splits EXP across alive heroes,
 * accumulates gold into [ZoneState], and fires [onReward] callbacks for UI updates.
 */
class RewardSystem(
    private val zoneState: ZoneState,
    private val onReward: (RewardEvent) -> Unit = {},
) : IteratingSystem(
    family = com.github.quillraven.fleks.family { all(EnemyComponent) }
) {
    companion object {
        private val log = logger<RewardSystem>()
    }

    override fun onTickEntity(entity: Entity) {
        val enemy = entity[EnemyComponent]
        if (enemy.isAlive) return

        // Gold reward
        val gold = enemy.goldReward.toLong()
        zoneState.totalGoldEarned += gold
        onReward(RewardEvent.GoldEarned(amount = gold))

        // EXP split across all alive heroes
        val aliveHeroes = world.family { all(HeroComponent) }
            .filter { world.mapper(HeroComponent)[it].isAlive }

        if (aliveHeroes.isNotEmpty()) {
            val expPerHero = (enemy.expReward / aliveHeroes.size).coerceAtLeast(1)
            aliveHeroes.forEach { heroEntity ->
                val hero = world.mapper(HeroComponent)[heroEntity]
                hero.exp += expPerHero
                zoneState.totalExpEarned += expPerHero

                // Level-up check
                while (hero.exp >= hero.expForNextLevel()) {
                    hero.exp -= hero.expForNextLevel()
                    hero.level++
                    hero.currentHp = hero.totalMaxHp // heal on level up
                    onReward(RewardEvent.HeroLevelUp(
                        heroClass = hero.heroClass.displayName,
                        newLevel  = hero.level,
                    ))
                    log.info { "${hero.heroClass.displayName} leveled up to ${hero.level}!" }
                }
            }
        }

        log.debug { "Rewards: +$gold gold, +${enemy.expReward} exp" }
    }
}

/** Events emitted by RewardSystem consumed by UI/HUD. */
sealed class RewardEvent {
    data class GoldEarned(val amount: Long) : RewardEvent()
    data class HeroLevelUp(val heroClass: String, val newLevel: Int) : RewardEvent()
}
