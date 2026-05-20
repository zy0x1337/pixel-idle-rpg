package dev.zy0x.pixelidlerpg.save

import com.badlogic.gdx.Gdx
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ktx.log.logger

/**
 * Handles reading and writing SaveGame to libGDX Preferences.
 * No file I/O — uses Preferences API for cross-platform compatibility.
 */
object SaveManager {

    private val log = logger<SaveManager>()
    private const val PREFS_NAME = "pixel_idle_rpg_save"
    private const val KEY_SAVE = "save_data"

    private val json = Json {
        ignoreUnknownKeys = true   // forward-compatible: safe to add new fields
        encodeDefaults = true
    }

    fun save(saveGame: SaveGame) {
        try {
            val prefs = Gdx.app.getPreferences(PREFS_NAME)
            prefs.putString(KEY_SAVE, json.encodeToString(saveGame))
            prefs.flush()
            log.info { "Game saved. Gold=${saveGame.gold}, Zone=${saveGame.currentZoneId}" }
        } catch (e: Exception) {
            log.error { "Failed to save game: ${e.message}" }
        }
    }

    fun load(): SaveGame {
        return try {
            val prefs = Gdx.app.getPreferences(PREFS_NAME)
            val raw = prefs.getString(KEY_SAVE, "")
            if (raw.isBlank()) {
                log.info { "No save found, starting fresh." }
                SaveGame()
            } else {
                json.decodeFromString<SaveGame>(raw).also {
                    log.info { "Save loaded. Gold=${it.gold}, Zone=${it.currentZoneId}" }
                }
            }
        } catch (e: Exception) {
            log.error { "Failed to load save, resetting: ${e.message}" }
            SaveGame()
        }
    }

    fun deleteSave() {
        val prefs = Gdx.app.getPreferences(PREFS_NAME)
        prefs.remove(KEY_SAVE)
        prefs.flush()
        log.info { "Save deleted." }
    }

    /** Calculate idle rewards for time spent offline. */
    fun calculateOfflineRewards(save: SaveGame, nowTimestamp: Long): OfflineRewards {
        if (save.lastPlayedTimestamp == 0L) return OfflineRewards()
        val secondsAway = ((nowTimestamp - save.lastPlayedTimestamp) / 1000L)
            .coerceIn(0L, 8L * 3600L) // cap at 8 hours
        // Placeholder rate: 10 gold/sec, 50% offline efficiency
        val goldEarned = (secondsAway * 10 * 0.5).toLong()
        return OfflineRewards(
            secondsAway = secondsAway,
            goldEarned = goldEarned,
        )
    }
}

data class OfflineRewards(
    val secondsAway: Long = 0L,
    val goldEarned: Long = 0L,
)
