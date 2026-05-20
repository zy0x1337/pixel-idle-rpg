package dev.zy0x.pixelidlerpg.ui.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Label
import dev.zy0x.pixelidlerpg.ui.UiSkin

/** Top-right wave indicator: "Wave 3/10" or "★ BOSS" in gold when boss round. */
class WaveLabel : Group() {

    private val label = Label("Wave 1/10", UiSkin.labelStyle(Color.WHITE))

    init {
        setSize(100f, 18f)
        addActor(label)
    }

    fun setWave(current: Int, total: Int) {
        if (current % 10 == 0) {
            label.setText("★ BOSS")
            label.color = Color.GOLD
        } else {
            label.setText("Wave $current")
            label.color = Color.WHITE
        }
    }
}
