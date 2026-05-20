package dev.zy0x.pixelidlerpg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import dev.zy0x.pixelidlerpg.PixelIdleRpgGame
import ktx.app.KtxScreen
import ktx.log.logger

/**
 * Main menu screen.
 * Shows logo, New Game / Continue / Settings buttons.
 */
class MainMenuScreen(private val game: PixelIdleRpgGame) : KtxScreen {

    companion object {
        private val log = logger<MainMenuScreen>()
    }

    override fun show() {
        log.info { "MainMenuScreen shown" }
        // TODO: Build Scene2D stage with KTX scene2d DSL
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.12f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // TODO: stage.act(delta) + stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        // TODO: viewport.update(width, height, true)
    }

    override fun dispose() {
        // TODO: stage.dispose()
    }
}
