package dev.zy0x.pixelidlerpg.domain

/**
 * Game zones / world areas.
 * Each zone has a tiledMap asset path and a recommended party power level.
 */
enum class Zone(
    val displayName: String,
    val tiledMapPath: String,
    val recommendedPower: Int,
    val goldMultiplier: Float = 1.0f,
) {
    VERDANT_FOREST  ("Verdant Forest",  "tilemaps/zone_01_forest.tmx",  power = 0,    goldMultiplier = 1.0f),
    DARK_CAVES      ("Dark Caves",      "tilemaps/zone_02_caves.tmx",   power = 200,  goldMultiplier = 1.5f),
    RUINED_CASTLE   ("Ruined Castle",   "tilemaps/zone_03_castle.tmx",  power = 600,  goldMultiplier = 2.5f),
    MAGMA_DEPTHS    ("Magma Depths",    "tilemaps/zone_04_magma.tmx",   power = 1500, goldMultiplier = 5.0f),
    THE_VOID        ("The Void",        "tilemaps/zone_05_void.tmx",    power = 5000, goldMultiplier = 15.0f),
}
