package game.map

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer

/**
 * Loads and manages TiledMap assets.
 * Usage:
 *   val loader = TiledMapLoader(assetManager)
 *   loader.loadZone(1)          // queues load
 *   assetManager.finishLoading()
 *   val renderer = loader.getRenderer(1)
 */
class TiledMapLoader(private val assets: AssetManager) {

    companion object {
        private const val UNIT_SCALE = 1f  // 1 tile = 1 world unit (pixels 1:1)
        fun pathForZone(zone: Int) = "tilemaps/zone$zone.tmx"
    }

    init {
        assets.setLoader(TiledMap::class.java, TmxMapLoader())
    }

    private val renderers = mutableMapOf<Int, OrthogonalTiledMapRenderer>()
    private val maps      = mutableMapOf<Int, TiledMap>()

    /** Queue a zone map for async loading via AssetManager. */
    fun loadZone(zone: Int) {
        val path = pathForZone(zone)
        if (!assets.isLoaded(path)) {
            assets.load(path, TiledMap::class.java)
        }
    }

    /**
     * Call after assetManager.finishLoading() or inside a loading screen.
     * Returns the OrthogonalTiledMapRenderer ready to use.
     */
    fun getRenderer(zone: Int): OrthogonalTiledMapRenderer {
        return renderers.getOrPut(zone) {
            val map = assets.get(pathForZone(zone), TiledMap::class.java)
            maps[zone] = map
            OrthogonalTiledMapRenderer(map, UNIT_SCALE)
        }
    }

    fun getMap(zone: Int): TiledMap = maps[zone]
        ?: error("Zone $zone map not loaded yet — call loadZone() first")

    /**
     * Render specific layers by index.
     * Typical call:
     *   renderer.render(intArrayOf(LAYER_BG, LAYER_FLOOR))   // behind sprites
     *   renderer.render(intArrayOf(LAYER_FOREGROUND))         // in front of sprites
     */
    fun renderLayers(zone: Int, layerIndices: IntArray) {
        renderers[zone]?.render(layerIndices)
    }

    fun dispose() {
        renderers.values.forEach { it.dispose() }
        renderers.clear()
        maps.clear()
    }

    companion object LayerIndex {
        const val LAYER_BACKGROUND  = 0
        const val LAYER_FLOOR       = 1
        const val LAYER_WALLS       = 2
        const val LAYER_FOREGROUND  = 3
        // LAYER_OBJECTS (index 4) is an ObjectLayer — not rendered, only read by MapObjectSpawner
    }
}
