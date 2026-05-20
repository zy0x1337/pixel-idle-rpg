package dev.zy0x.pixelidlerpg.save

import kotlinx.serialization.Serializable

/**
 * Root save game data structure.
 * Serialized to/from JSON via libGDX Preferences.
 */
@Serializable
data class SaveGame(
    val version: Int = 1,
    val gold: Long = 0L,
    val gems: Int = 0,
    val prestigeShards: Int = 0,
    val prestigeCount: Int = 0,
    val lastPlayedTimestamp: Long = 0L,
    val currentZoneId: String = "VERDANT_FOREST",
    val heroes: List<SavedHero> = emptyList(),
)

@Serializable
data class SavedHero(
    val heroClassId: String,
    val level: Int = 1,
    val exp: Long = 0L,
    val equippedItemIds: Map<String, String> = emptyMap(), // slot -> itemId
)
