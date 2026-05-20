package dev.zy0x.pixelidlerpg.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import dev.zy0x.pixelidlerpg.PixelIdleRpgGame

/**
 * Desktop (LWJGL3) entry point.
 * Used for local development and testing — faster iteration than deploying to Android.
 */
fun main() {
    val config = Lwjgl3ApplicationConfiguration().apply {
        setTitle("Pixel Idle RPG")
        // Simulate a typical portrait Android screen size on desktop
        setWindowedMode(405, 720)
        setWindowIcon("icons/icon128.png", "icons/icon64.png", "icons/icon32.png", "icons/icon16.png")
        setForegroundFPS(60)
        useVsync(true)
    }
    Lwjgl3Application(PixelIdleRpgGame(), config)
}
