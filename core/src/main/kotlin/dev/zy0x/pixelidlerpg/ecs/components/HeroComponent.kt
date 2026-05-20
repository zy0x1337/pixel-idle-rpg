package dev.zy0x.pixelidlerpg.ecs.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import dev.zy0x.pixelidlerpg.domain.HeroClass
import dev.zy0x.pixelidlerpg.domain.ItemSlot
import dev.zy0x.pixelidlerpg.domain.Item

/**
 * Fleks ECS component representing a Hero entity.
 */
data class HeroComponent(
    val heroClass: HeroClass,
    var level: Int = 1,
    var exp: Long = 0L,
    var currentHp: Int = heroClass.baseStats.maxHp,
    var equipment: Map<ItemSlot, Item> = emptyMap(),
) : Component<HeroComponent> {
    override fun type() = HeroComponent
    companion object : ComponentType<HeroComponent>()

    /** Derived total stats including equipment bonuses. */
    val totalAtk: Int get() = heroClass.baseStats.atk +
        equipment.values.sumOf { it.atkBonus } +
        (level - 1) * 2

    val totalDef: Int get() = heroClass.baseStats.def +
        equipment.values.sumOf { it.defBonus }

    val totalMaxHp: Int get() = heroClass.baseStats.maxHp +
        equipment.values.sumOf { it.hpBonus } +
        (level - 1) * 5

    val isAlive: Boolean get() = currentHp > 0

    fun expForNextLevel(): Long = (level * level * 100L)
}
