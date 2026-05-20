package dev.zy0x.pixelidlerpg.rendering

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import dev.zy0x.pixelidlerpg.assets.AssetDescriptors
import dev.zy0x.pixelidlerpg.assets.GameAssetManager
import dev.zy0x.pixelidlerpg.rendering.animation.AnimationController
import dev.zy0x.pixelidlerpg.rendering.animation.SpriteAnimation
import ktx.graphics.use
import ktx.log.logger

/**
 * SpriteRenderer — owns the [SpriteBatch] and [FitViewport].
 *
 * Virtual resolution: 360x640 px (portrait, 16:9).
 * All game-world coordinates are in this virtual space.
 * libGDX FitViewport scales + letterboxes to any physical screen.
 *
 * Rendering order each frame:
 *   1. Background / tilemap (handled by TiledMapRenderer in GameScreen)
 *   2. Enemies  — rendered at [ENEMY_X]
 *   3. Heroes   — rendered at [HERO_BASE_X] + offset per slot
 *   4. VFX      — hit sparks, level-up bursts (future)
 */
class SpriteRenderer(
    private val assets: GameAssetManager,
) : Disposable {

    companion object {
        private val log = logger<SpriteRenderer>()

        // Virtual resolution (portrait)
        const val V_WIDTH  = 360f
        const val V_HEIGHT = 640f

        // World positions (virtual px)
        const val GROUND_Y     = 120f  // ground line
        const val ENEMY_X      = 240f  // enemy stands right-side
        const val HERO_BASE_X  =  80f  // first hero slot
        const val HERO_SLOT_W  =  48f  // spacing between hero slots
        const val SPRITE_SCALE =  3f   // pixel-art ×3 upscale (16px → 48px)
    }

    val camera   = OrthographicCamera()
    val viewport = FitViewport(V_WIDTH, V_HEIGHT, camera)
    val batch    = SpriteBatch()

    /** Per-entity animation controllers. Key = entity ID string. */
    private val heroControllers:  MutableMap<String, AnimationController> = mutableMapOf()
    private val enemyControllers: MutableMap<String, AnimationController> = mutableMapOf()

    private val heroAtlas: TextureAtlas  by lazy { assets.asset(AssetDescriptors.HEROES) }
    private val enemyAtlas: TextureAtlas by lazy { assets.asset(AssetDescriptors.ENEMIES) }

    init {
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT)
        log.info { "SpriteRenderer initialised. Virtual: ${V_WIDTH}x${V_HEIGHT}" }
    }

    /**
     * Render all visible sprites for this frame.
     *
     * @param delta   Frame delta time in seconds
     * @param heroes  List of (entityId, heroClassKey, isAlive)
     * @param enemy   Triple of (entityId, enemyTypeKey, isAlive), or null if none
     */
    fun render(
        delta: Float,
        heroes: List<RenderableHero>,
        enemy: RenderableEnemy?,
    ) {
        camera.update()
        batch.projectionMatrix = camera.combined

        batch.use {
            // Draw heroes
            heroes.forEachIndexed { slot, hero ->
                val ctrl = heroControllers.getOrPut(hero.id) {
                    AnimationController(heroAtlas, prefix = hero.classKey)
                }
                ctrl.setState(if (hero.isAlive) SpriteAnimation.IDLE else SpriteAnimation.DEAD)
                val frame = ctrl.currentFrame(delta)
                val x = HERO_BASE_X + slot * HERO_SLOT_W
                it.draw(
                    frame,
                    x - frame.regionWidth  * SPRITE_SCALE / 2f,
                    GROUND_Y,
                    frame.regionWidth  * SPRITE_SCALE,
                    frame.regionHeight * SPRITE_SCALE,
                )
            }

            // Draw enemy
            if (enemy != null) {
                val ctrl = enemyControllers.getOrPut(enemy.id) {
                    AnimationController(enemyAtlas, prefix = enemy.typeKey)
                }
                ctrl.setState(if (enemy.isAlive) SpriteAnimation.IDLE else SpriteAnimation.DEAD)
                val frame = ctrl.currentFrame(delta)
                // Flip horizontally so enemy faces left
                it.draw(
                    frame,
                    ENEMY_X - frame.regionWidth  * SPRITE_SCALE / 2f,
                    GROUND_Y,
                    frame.regionWidth  * SPRITE_SCALE,
                    frame.regionHeight * SPRITE_SCALE,
                )
            }
        }
    }

    fun resize(width: Int, height: Int) = viewport.update(width, height, true)

    override fun dispose() {
        batch.dispose()
        log.info { "SpriteRenderer disposed" }
    }
}

// ---- Data transfer objects for the render layer ----------------------------

data class RenderableHero(
    val id: String,
    val classKey: String,    // e.g. "warrior", "mage" — matches atlas region prefix
    val isAlive: Boolean,
)

data class RenderableEnemy(
    val id: String,
    val typeKey: String,     // e.g. "slime", "goblin" — matches atlas region prefix
    val isAlive: Boolean,
)
