package game.map

/**
 * Static metadata for each zone.
 * Extend this as new zones are added.
 *
 * Zone 1 — Crystal Cavern
 * Zone 2 — (TBD)
 */
enum class ZoneData(
    val zoneId:       Int,
    val displayName:  String,
    val tmxPath:      String,
    val bgPath:       String,
    val musicPath:    String,
    val ambientPath:  String,
    val tilesetPath:  String,
    val bossWave:     Int       // wave number that spawns the boss
) {
    CRYSTAL_CAVERN(
        zoneId      = 1,
        displayName = "Crystal Cavern",
        tmxPath     = "tilemaps/zone1.tmx",
        bgPath      = "sprites/src/backgrounds/zone1_background.png",
        musicPath   = "audio/music/zone1_cavern.ogg",
        ambientPath = "audio/sfx/cavern_ambience.ogg",
        tilesetPath = "tilemaps/tilesets/zone1_tileset_full.png",
        bossWave    = 10
    );

    companion object {
        fun fromId(id: Int) = entries.firstOrNull { it.zoneId == id }
            ?: error("No ZoneData defined for zone id=$id")
    }
}
