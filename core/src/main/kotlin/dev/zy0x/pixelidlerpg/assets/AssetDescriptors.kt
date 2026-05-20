package dev.zy0x.pixelidlerpg.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader

/**
 * Central registry of all asset paths used in the game.
 *
 * Naming conventions:
 *   sprites/atlas/<name>.atlas  — TextureAtlas (packed by gdx-tools / libgdx-texturepacker)
 *   tilemaps/<zone>.tmx         — Tiled maps
 *   sounds/sfx/<name>.ogg       — Short sound effects
 *   sounds/music/<name>.ogg     — Looping background music
 *
 * Each atlas corresponds to one packed sheet created with Nano Banana 2 exports.
 * Atlas regions follow the convention: <entity>_<action>_<frame>
 *   e.g. "warrior_idle_00", "warrior_attack_01", "slime_die_02"
 */
object AssetDescriptors {

    // --- Sprite Atlases ---
    val HEROES  = AssetDescriptor("sprites/atlas/heroes.atlas",  TextureAtlas::class.java)
    val ENEMIES = AssetDescriptor("sprites/atlas/enemies.atlas", TextureAtlas::class.java)
    val UI      = AssetDescriptor("sprites/atlas/ui.atlas",      TextureAtlas::class.java)
    val ITEMS   = AssetDescriptor("sprites/atlas/items.atlas",   TextureAtlas::class.java)
    val VFX     = AssetDescriptor("sprites/atlas/vfx.atlas",     TextureAtlas::class.java)

    // --- Tiled Maps ---
    val ZONE_1_VERDANT  = AssetDescriptor("tilemaps/zone1_verdant_forest.tmx",  TiledMap::class.java)
    val ZONE_2_CAVES    = AssetDescriptor("tilemaps/zone2_dark_caves.tmx",       TiledMap::class.java)

    // --- SFX ---
    val SFX_HIT        = AssetDescriptor("sounds/sfx/hit.ogg",       Sound::class.java)
    val SFX_LEVEL_UP   = AssetDescriptor("sounds/sfx/levelup.ogg",   Sound::class.java)
    val SFX_GOLD       = AssetDescriptor("sounds/sfx/gold.ogg",      Sound::class.java)
    val SFX_BOSS_ROAR  = AssetDescriptor("sounds/sfx/boss_roar.ogg", Sound::class.java)

    // --- Music ---
    val MUSIC_FOREST   = AssetDescriptor("sounds/music/verdant_forest.ogg", Music::class.java)
    val MUSIC_CAVES    = AssetDescriptor("sounds/music/dark_caves.ogg",     Music::class.java)
    val MUSIC_MENU     = AssetDescriptor("sounds/music/main_menu.ogg",      Music::class.java)
}
