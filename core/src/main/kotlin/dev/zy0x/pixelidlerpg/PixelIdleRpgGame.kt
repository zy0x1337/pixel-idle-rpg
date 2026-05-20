package dev.zy0x.pixelidlerpg

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import dev.zy0x.pixelidlerpg.screens.LoadingScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.log.logger

/**
 * Main game class. Entry point for all platforms.
 * Uses KTX KtxGame for Kotlin-idiomatic screen management.
 */
class PixelIdleRpgGame : KtxGame<KtxScreen>() {

    companion object {
        private val log = logger<PixelIdleRpgGame>()
    }

    override fun create() {
        log.info { "PixelIdleRpgGame starting up..." }
        addScreen(LoadingScreen(this))
        setScreen<LoadingScreen>()
    }

    override fun dispose() {
        log.info { "PixelIdleRpgGame disposing..." }
        super.dispose()
    }
}
