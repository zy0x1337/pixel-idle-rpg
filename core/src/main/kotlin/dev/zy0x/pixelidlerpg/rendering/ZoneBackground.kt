package dev.zy0x.pixelidlerpg.rendering

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.Disposable
import dev.zy0x.pixelidlerpg.domain.Zone
import ktx.log.logger

/**
 * ZoneBackground — wraps [OrthogonalTiledMapRenderer] for pixel-perfect tilemap rendering.
 *
 * Virtual tile size: 16 px.
 * Unit scale = 1f (1 tile = 1 world unit, camera covers V_WIDTH/16 x V_HEIGHT/16 tiles).
 *
 * Rendering order in GameScreen.render():
 *   1. zoneBackground.renderBackground(camera)  ← floor + far-bg layers
 *   2. spriteRenderer.render(…)                 ← heroes + enemies
 *   3. zoneBackground.renderForeground(camera)   ← foreground overlap layer (optional)
 *   4. hudStage.draw()                           ← UI on top
 *
 * Layers in the .tmx file must be named:
 *   "background"  — sky, far trees
 *   "midground"   — ground, platforms (collision layer)
 *   "foreground"  — overlapping leaves, torches, etc.
 */
class ZoneBackground(tiledMap: TiledMap) : Disposable {

    companion object {
        private val log = logger<ZoneBackground>()
        // Unit scale: 1 world unit = 16px tile. SpriteRenderer virtual width = 360px = 22.5 tiles.
        private const val UNIT_SCALE = 1f / 16f
    }

    private val mapRenderer = OrthogonalTiledMapRenderer(tiledMap, UNIT_SCALE)

    private val bgLayerIndices:  IntArray
    private val fgLayerIndices:  IntArray

    init {
        val layers = tiledMap.layers
        bgLayerIndices = (0 until layers.count)
            .filter { layers.get(it).name != "foreground" }
            .toIntArray()
        fgLayerIndices = (0 until layers.count)
            .filter { layers.get(it).name == "foreground" }
            .toIntArray()

        log.info { "ZoneBackground ready. BG layers: ${bgLayerIndices.size}, FG layers: ${fgLayerIndices.size}" }
    }

    /** Render all non-foreground layers. Call BEFORE sprite rendering. */
    fun renderBackground(camera: OrthographicCamera) {
        mapRenderer.setView(camera)
        mapRenderer.render(bgLayerIndices)
    }

    /** Render foreground overlap layer. Call AFTER sprite rendering. */
    fun renderForeground(camera: OrthographicCamera) {
        if (fgLayerIndices.isEmpty()) return
        mapRenderer.setView(camera)
        mapRenderer.render(fgLayerIndices)
    }

    override fun dispose() {
        mapRenderer.dispose()
        log.info { "ZoneBackground disposed" }
    }
}
