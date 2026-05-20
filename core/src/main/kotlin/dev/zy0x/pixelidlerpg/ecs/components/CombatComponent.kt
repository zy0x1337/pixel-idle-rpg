package dev.zy0x.pixelidlerpg.ecs.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

/**
 * Tracks combat state and attack timer for any combatant entity.
 */
data class CombatComponent(
    val attackInterval: Float = 1.0f, // seconds between attacks
    var attackTimer: Float = 0f,
    var isAttacking: Boolean = false,
) : Component<CombatComponent> {
    override fun type() = CombatComponent
    companion object : ComponentType<CombatComponent>()

    fun tickTimer(delta: Float): Boolean {
        attackTimer += delta
        return if (attackTimer >= attackInterval) {
            attackTimer = 0f
            true // ready to attack
        } else false
    }
}
