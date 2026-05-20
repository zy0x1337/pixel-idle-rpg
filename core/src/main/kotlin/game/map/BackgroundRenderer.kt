package game.map

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils

/**
 * Renders a fullscreen parallax background behind the TiledMap.
 *
 * Three layers at different scroll speeds create depth:
 *   back   → 0.05x camera movement  (far distant cavern silhouettes)
 *   mid    → 0.20x camera movement  (crystal wall formations)
 *   front  → 0.50x camera movement  (near floor detail)
 *
 * Usage in GameScreen.render():
 *   backgroundRenderer.render(batch, camera)
 *   tiledMapRenderer.render(...)       // Tile layers on top
 *   ... sprites ...
 *   hudStage.draw()
 */
class BackgroundRenderer(
    private val back:  TextureRegion,   // zone1_background.png — back slice
    private val mid:   TextureRegion,   // zone1_background.png — mid slice
    private val front: TextureRegion,   // zone1_background.png — front slice
    private val screenW: Float,
    private val screenH: Float
) {

    companion object {
        const val SPEED_BACK  = 0.05f
        const val SPEED_MID   = 0.20f
        const val SPEED_FRONT = 0.50f
    }

    /**
     * @param batch  an already-begun SpriteBatch
     * @param camera the game's OrthographicCamera
     */
    fun render(batch: SpriteBatch, camera: OrthographicCamera) {
        val cx = camera.position.x
        val cy = camera.position.y

        drawLayer(batch, back,  cx * SPEED_BACK,  cy * SPEED_BACK)
        drawLayer(batch, mid,   cx * SPEED_MID,   cy * SPEED_MID)
        drawLayer(batch, front, cx * SPEED_FRONT, cy * SPEED_FRONT)
    }

    private fun drawLayer(
        batch:  SpriteBatch,
        region: TextureRegion,
        scrollX: Float,
        scrollY: Float
    ) {
        // Wrap the offset so the background tiles seamlessly
        val ox = MathUtils.floor(scrollX) % region.regionWidth
        val oy = MathUtils.floor(scrollY) % region.regionHeight

        batch.draw(
            region,
            -ox.toFloat(),
            -oy.toFloat(),
            screenW + region.regionWidth.toFloat(),
            screenH + region.regionHeight.toFloat()
        )
    }
}
