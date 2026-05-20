package dev.zy0x.pixelidlerpg.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import dev.zy0x.pixelidlerpg.PixelIdleRpgGame
import ktx.app.KtxScreen
import ktx.log.logger

/**
 * Core gameplay screen.
 * Hosts the Fleks ECS world, combat tick, rendering pipeline.
 */
class GameScreen(private val game: PixelIdleRpgGame) : KtxScreen {

    companion object {
        private val log = logger<GameScreen>()
    }

    override fun show() {
        log.info { "GameScreen shown" }
        // TODO: Initialize Fleks world
        // TODO: Spawn hero + first enemy
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.08f, 0.1f, 0.12f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        // TODO: world.update(delta)
        // TODO: SpriteBatch render
        // TODO: HUD render
    }

    override fun resize(width: Int, height: Int) {
        // TODO: viewport.update(width, height, true)
    }

    override fun dispose() {
        // TODO: world.dispose()
    }
}
