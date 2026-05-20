package game.map

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * Checks each frame if the hero has stepped into the zone_exit rectangle.
 * When triggered, calls [onZoneComplete] with the next zone id.
 *
 * Wired in GameScreen:
 *   engine.addSystem(ZoneTransitionSystem(spawnData.zoneExit, heroPos) { nextId ->
 *       game.setScreen(LoadingScreen(nextId))
 *   })
 */
class ZoneTransitionSystem(
    private val exitRect:       Rectangle?,
    private val heroPosition:   Vector2,
    private val onZoneComplete: (nextZoneId: Int) -> Unit
) : EntitySystem() {

    private var currentZone = 1
    private var triggered   = false

    override fun update(deltaTime: Float) {
        if (triggered || exitRect == null) return
        if (exitRect.contains(heroPosition)) {
            triggered = true
            onZoneComplete(currentZone + 1)
        }
    }

    fun reset(newZone: Int) {
        currentZone = newZone
        triggered   = false
    }
}
