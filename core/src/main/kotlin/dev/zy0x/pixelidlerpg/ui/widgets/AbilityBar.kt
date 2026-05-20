package dev.zy0x.pixelidlerpg.ui.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import dev.zy0x.pixelidlerpg.assets.AssetDescriptors
import dev.zy0x.pixelidlerpg.assets.GameAssetManager
import dev.zy0x.pixelidlerpg.ui.UiSkin

/**
 * AbilityBar — a row of tappable skill buttons, one per hero.
 *
 * Each button has:
 *  - Icon from UI atlas ("ui/ability_<slot>")
 *  - Cooldown overlay ProgressBar that sweeps from 1→0 (pie-style: TODO in v0.2)
 *  - Disabled state while on cooldown
 *
 * Touch events are forwarded to the [onAbilityTapped] callback (set by GameScreen).
 */
class AbilityBar(
    assets: GameAssetManager,
    private val slotCount: Int,
) : Group() {

    companion object {
        const val BUTTON_SIZE = 48f
        const val BUTTON_GAP  = 12f
        fun totalWidth(slots: Int) = slots * BUTTON_SIZE + (slots - 1) * BUTTON_GAP
    }

    var onAbilityTapped: ((slot: Int) -> Unit)? = null

    private val buttons: List<ImageButton>
    private val cooldownBars: List<ProgressBar>

    init {
        val uiAtlas = assets.asset(AssetDescriptors.UI)
        setSize(totalWidth(slotCount), BUTTON_SIZE)

        buttons = (0 until slotCount).map { i ->
            val region   = uiAtlas.findRegion("ability_button_frame")
            val btnStyle = UiSkin.imageButtonStyle(region)
            val btn      = ImageButton(btnStyle)
            btn.setSize(BUTTON_SIZE, BUTTON_SIZE)
            btn.setPosition(i * (BUTTON_SIZE + BUTTON_GAP), 0f)
            btn.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    if (!btn.isDisabled) onAbilityTapped?.invoke(i)
                }
            })
            addActor(btn)
            btn
        }

        cooldownBars = (0 until slotCount).map { i ->
            val bar = ProgressBar(0f, 1f, 0.01f, true /* vertical */, UiSkin.cooldownBarStyle())
            bar.setSize(BUTTON_SIZE, BUTTON_SIZE)
            bar.setPosition(i * (BUTTON_SIZE + BUTTON_GAP), 0f)
            bar.isVisible = false
            addActor(bar)
            bar
        }
    }

    /**
     * Update cooldown state for a slot.
     * @param progress 0f = ready, 1f = just used (full cooldown)
     */
    fun setCooldown(slot: Int, progress: Float) {
        val bar = cooldownBars.getOrNull(slot) ?: return
        val btn = buttons.getOrNull(slot) ?: return
        bar.isVisible = progress > 0f
        bar.value = progress
        btn.isDisabled = progress > 0f
    }
}
