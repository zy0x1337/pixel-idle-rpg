package dev.zy0x.pixelidlerpg.domain

import kotlinx.serialization.Serializable

/**
 * Base stat block shared by heroes and enemies.
 */
@Serializable
data class Stats(
    val maxHp: Int,
    val atk: Int,
    val def: Int,
    val spd: Float = 1.0f,   // attack speed multiplier
    val luck: Float = 1.0f,  // drop rate multiplier
) {
    val isAlive get() = maxHp > 0
}
