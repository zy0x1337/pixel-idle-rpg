package dev.zy0x.pixelidlerpg

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

/**
 * Android entry point for the game.
 */
class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration().apply {
            // Pixel-perfect rendering — disable anti-aliasing
            numSamples = 0
            // Keep screen on while playing
            useWakelock = true
            // Hide status bar for full immersive game experience
            useImmersiveMode = true
        }
        initialize(PixelIdleRpgGame(), config)
    }
}
