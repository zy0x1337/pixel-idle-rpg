package dev.zy0x.pixelidlerpg.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

/**
 * UiSkin — centralised factory for Scene2D widget styles.
 *
 * Uses libGDX built-in BitmapFont (Gdx default font) for now.
 * TODO Phase 2: replace with a proper Skin loaded from assets/ui/ui.skin
 *              and a pixel-art bitmap font from Nano Banana 2 / Hiero.
 *
 * Color palette (matches Tiled zone moods):
 *   HP bar fill  — #5CB85C (green) → red at <25%
 *   Boss HP bar  — #D4AF37 (gold)
 *   Gold text    — #FFD700
 *   Default text — #FFFFFF
 */
object UiSkin {

    // Font size tokens
    const val FONT_SIZE_NORMAL = 12
    const val FONT_SIZE_SMALL  = 9
    const val FONT_SIZE_LARGE  = 16

    // Color constants
    val COLOR_GOLD    = Color(1f, 0.84f, 0f, 1f)
    val COLOR_HP_GREEN = Color(0.36f, 0.72f, 0.36f, 1f)
    val COLOR_HP_RED   = Color(0.85f, 0.22f, 0.22f, 1f)
    val COLOR_HP_BOSS  = Color(0.83f, 0.69f, 0.22f, 1f)
    val COLOR_COOLDOWN = Color(0.1f, 0.1f, 0.1f, 0.7f)

    /** Default libGDX BitmapFont — replace with pixel font in Phase 2. */
    private val defaultFont = BitmapFont()

    fun labelStyle(
        color: Color = Color.WHITE,
        fontSize: Int = FONT_SIZE_NORMAL,
    ): Label.LabelStyle = Label.LabelStyle(defaultFont, color)

    fun hpBarStyle(isBoss: Boolean): ProgressBar.ProgressBarStyle {
        val bg   = solid(Color(0.15f, 0.15f, 0.15f, 0.8f), 1, 10)
        val fill = solid(if (isBoss) COLOR_HP_BOSS else COLOR_HP_GREEN, 1, 10)
        return ProgressBar.ProgressBarStyle(bg, fill).also {
            it.knobBefore = fill
        }
    }

    fun cooldownBarStyle(): ProgressBar.ProgressBarStyle {
        val bg   = solid(Color(0f, 0f, 0f, 0f), 1, 1)
        val fill = solid(COLOR_COOLDOWN, 1, 1)
        return ProgressBar.ProgressBarStyle(bg, fill).also {
            it.knobBefore = fill
        }
    }

    fun imageButtonStyle(region: com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion?): ImageButton.ImageButtonStyle {
        val style = ImageButton.ImageButtonStyle()
        if (region != null) {
            style.up   = TextureRegionDrawable(region)
            style.down = TextureRegionDrawable(region).also { d ->
                d.minWidth  = region.regionWidth  * 0.95f
                d.minHeight = region.regionHeight * 0.95f
            }
        }
        return style
    }

    fun placeholderDrawable(color: Color): Drawable {
        val px = Pixmap(1, 1, Pixmap.Format.RGBA8888)
        px.setColor(color)
        px.fill()
        return TextureRegionDrawable(TextureRegion(Texture(px)))
    }

    /** Creates a 1px solid color drawable. */
    private fun solid(color: Color, w: Int, h: Int): Drawable {
        val px = Pixmap(w, h, Pixmap.Format.RGBA8888)
        px.setColor(color)
        px.fill()
        return TextureRegionDrawable(TextureRegion(Texture(px)))
    }
}
