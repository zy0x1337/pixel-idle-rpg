package dev.zy0x.pixelidlerpg.domain

import kotlinx.serialization.Serializable

/**
 * Equipment slot types.
 */
enum class ItemSlot {
    WEAPON, ARMOR, ACCESSORY
}

/**
 * A single equippable item.
 */
@Serializable
data class Item(
    val id: String,
    val name: String,
    val slot: ItemSlot,
    val rarity: ItemRarity,
    val atkBonus: Int = 0,
    val defBonus: Int = 0,
    val hpBonus: Int = 0,
    val spdBonus: Float = 0f,
    val luckBonus: Float = 0f,
)
