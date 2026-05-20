package game.map

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/**
 * Reads the "objects" ObjectLayer from a TiledMap and exposes
 * typed spawn data to the rest of the game.
 *
 * Object names set in Tiled:
 *   hero_spawn        → MapPoint  — warrior start position
 *   enemy_spawn_01..N → MapPoint  — regular enemy spawn positions
 *   boss_spawn        → MapPoint  — boss spawn position
 *   zone_exit         → MapRect   — rectangle trigger to advance zone
 *   camera_bounds     → MapRect   — hard camera clamp area
 */
class MapObjectSpawner(private val map: TiledMap) {

    data class MapPoint(val name: String, val x: Float, val y: Float) {
        fun toVector2() = Vector2(x, y)
    }

    data class MapRect(val name: String, val rect: Rectangle)

    data class ZoneSpawnData(
        val heroSpawn:     MapPoint,
        val enemySpawns:   List<MapPoint>,
        val bossSpawn:     MapPoint?,
        val zoneExit:      MapRect?,
        val cameraBounds:  MapRect?
    )

    private val objectLayer get() = map.layers["objects"]
        ?: error("TiledMap is missing an 'objects' layer — add it in Tiled")

    fun parse(): ZoneSpawnData {
        val points = mutableListOf<MapPoint>()
        val rects  = mutableListOf<MapRect>()

        objectLayer.objects.forEach { obj ->
            when {
                obj.isPoint() -> points.add(obj.toMapPoint())
                obj is RectangleMapObject -> rects.add(MapRect(obj.name ?: "", obj.rectangle))
            }
        }

        val heroSpawn    = points.firstOrNull { it.name == "hero_spawn" }
            ?: MapPoint("hero_spawn", 200f, 150f)  // fallback centre-bottom

        val enemySpawns  = points
            .filter { it.name.startsWith("enemy_spawn") }
            .sortedBy  { it.name }  // deterministic order: _01, _02, …

        val bossSpawn    = points.firstOrNull { it.name == "boss_spawn" }
        val zoneExit     = rects.firstOrNull  { it.name == "zone_exit" }
        val cameraBounds = rects.firstOrNull  { it.name == "camera_bounds" }

        return ZoneSpawnData(heroSpawn, enemySpawns, bossSpawn, zoneExit, cameraBounds)
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private fun MapObject.isPoint(): Boolean {
        // Tiled Point objects store x/y directly in properties
        return this !is RectangleMapObject &&
               this.properties.containsKey("x") &&
               this.properties.containsKey("y")
    }

    private fun MapObject.toMapPoint() = MapPoint(
        name = this.name ?: "",
        x    = this.properties.get("x", Float::class.java),
        y    = this.properties.get("y", Float::class.java)
    )
}
