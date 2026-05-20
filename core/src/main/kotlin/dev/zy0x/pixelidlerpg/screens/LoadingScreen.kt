package dev.zy0x.pixelidlerpg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import dev.zy0x.pixelidlerpg.PixelIdleRpgGame
import ktx.app.KtxScreen
import ktx.log.logger

/**
 * First screen shown on launch.
 * Handles asset loading; transitions to MainMenuScreen when done.
 */
class LoadingScreen(private val game: PixelIdleRpgGame) : KtxScreen {

    companion object {
        private val log = logger<LoadingScreen>()
    }

    override fun show() {
        log.info { "LoadingScreen shown" }
        // TODO: Queue assets into AssetManager (KTX async)
    }

    override fun render(delta: Float) {
        // Clear screen with dark pixel-art background
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // TODO: Draw loading bar
        // TODO: When assets done -> game.setScreen<MainMenuScreen>()
    }

    override fun dispose() {
        // Nothing to dispose yet
    }
}
