package dev.zy0x.pixelidlerpg.ui.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import dev.zy0x.pixelidlerpg.ui.UiSkin

/**
 * CombatLog — shows the last 4 combat events as floating, fading text lines.
 *
 * Each new line slides in from the bottom and fades out after 2.5s.
 * Lines older than [MAX_LINES] are removed from the stage.
 */
class CombatLog : Group() {

    companion object {
        private const val MAX_LINES    = 4
        private const val LINE_HEIGHT  = 14f
        private const val FADE_DELAY   = 1.8f
        private const val FADE_DURATION = 0.7f
    }

    private val lines = ArrayDeque<Label>()

    init {
        setSize(160f, MAX_LINES * LINE_HEIGHT)
    }

    fun addLine(text: String) {
        // Shift existing lines up
        lines.forEach { it.y += LINE_HEIGHT }

        val lbl = Label(text, UiSkin.labelStyle(Color.WHITE, fontSize = UiSkin.FONT_SIZE_SMALL))
        lbl.setPosition(0f, 0f)
        lbl.color.a = 1f
        lbl.addAction(
            Actions.sequence(
                Actions.delay(FADE_DELAY),
                Actions.fadeOut(FADE_DURATION),
                Actions.removeActor()
            )
        )
        addActor(lbl)
        lines.addFirst(lbl)

        // Cap at MAX_LINES
        while (lines.size > MAX_LINES) {
            lines.removeLast().remove()
        }
    }
}
