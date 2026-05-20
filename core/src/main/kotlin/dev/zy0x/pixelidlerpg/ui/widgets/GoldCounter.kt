package dev.zy0x.pixelidlerpg.ui.widgets

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import dev.zy0x.pixelidlerpg.assets.AssetDescriptors
import dev.zy0x.pixelidlerpg.assets.GameAssetManager
import dev.zy0x.pixelidlerpg.ui.UiSkin

/**
 * GoldCounter — coin icon + animated number label.
 *
 * Tween animation: when [setGold] is called with a new value, the displayed
 * number counts up from the previous value over 0.6s (handled by [GoldTween]).
 *
 * Atlas region: "ui/gold_icon" from the UI atlas.
 */
class GoldCounter(assets: GameAssetManager) : Group() {

    private val label = Label("0", UiSkin.labelStyle(UiSkin.COLOR_GOLD))
    private var displayedGold = 0L
    private var targetGold    = 0L
    private var tweenTimer    = 0f
    private val tweenDuration = 0.6f

    init {
        val uiAtlas = assets.asset(AssetDescriptors.UI)
        val coinRegion = uiAtlas.findRegion("gold_icon")
        if (coinRegion != null) {
            val icon = Image(TextureRegionDrawable(coinRegion))
            icon.setSize(16f, 16f)
            addActor(icon)
        }
        label.setPosition(20f, 0f)
        addActor(label)
        setSize(100f, 18f)
    }

    fun setGold(amount: Long) {
        targetGold = amount
        tweenTimer = 0f
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (displayedGold == targetGold) return

        tweenTimer += delta
        val progress = (tweenTimer / tweenDuration).coerceIn(0f, 1f)
        displayedGold = lerp(displayedGold, targetGold, progress)
        label.setText(formatGold(displayedGold))

        if (progress >= 1f) displayedGold = targetGold
    }

    private fun lerp(from: Long, to: Long, t: Float) =
        (from + (to - from) * t).toLong()

    private fun formatGold(amount: Long): String = when {
        amount >= 1_000_000 -> "${amount / 1_000_000}M"
        amount >= 1_000     -> "${amount / 1_000}K"
        else                -> amount.toString()
    }
}
