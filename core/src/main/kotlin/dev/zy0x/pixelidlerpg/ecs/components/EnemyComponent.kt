package dev.zy0x.pixelidlerpg.ecs.components

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import dev.zy0x.pixelidlerpg.domain.Stats

/**
 * Fleks ECS component representing an Enemy entity.
 */
data class EnemyComponent(
    val id: String,
    val displayName: String,
    val stats: Stats,
    var currentHp: Int = stats.maxHp,
    val goldReward: Int,
    val expReward: Int,
    val isBoss: Boolean = false,
) : Component<EnemyComponent> {
    override fun type() = EnemyComponent
    companion object : ComponentType<EnemyComponent>()

    val isAlive: Boolean get() = currentHp > 0
}
