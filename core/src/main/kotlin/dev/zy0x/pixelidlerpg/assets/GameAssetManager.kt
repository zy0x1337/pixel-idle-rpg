package dev.zy0x.pixelidlerpg.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import ktx.assets.async.AssetStorage
import ktx.log.logger

/**
 * Thin wrapper around libGDX [AssetManager].
 *
 * Queues all required assets in the correct order.
 * LoadingScreen polls [progress] each frame until [isLoaded] returns true,
 * then transitions to GameScreen.
 *
 * NOTE: Tiled maps require registering [TmxMapLoader] as a custom loader.
 */
class GameAssetManager : AssetManager() {

    companion object {
        private val log = logger<GameAssetManager>()
    }

    init {
        // Register Tiled map loader so .tmx files can be queued
        setLoader(com.badlogic.gdx.maps.tiled.TiledMap::class.java, TmxMapLoader())
    }

    /** Queue all assets needed for the main game. */
    fun queueAll() {
        load(AssetDescriptors.HEROES)
        load(AssetDescriptors.ENEMIES)
        load(AssetDescriptors.UI)
        load(AssetDescriptors.ITEMS)
        load(AssetDescriptors.VFX)
        load(AssetDescriptors.ZONE_1_VERDANT)
        load(AssetDescriptors.SFX_HIT)
        load(AssetDescriptors.SFX_LEVEL_UP)
        load(AssetDescriptors.SFX_GOLD)
        load(AssetDescriptors.MUSIC_FOREST)
        load(AssetDescriptors.MUSIC_MENU)
        log.info { "Asset queue filled. Total: $queuedAssets assets" }
    }

    /** Shorthand: typed get avoiding class parameter boilerplate. */
    inline fun <reified T> asset(descriptor: com.badlogic.gdx.assets.AssetDescriptor<T>): T =
        get(descriptor.fileName, T::class.java)
}
