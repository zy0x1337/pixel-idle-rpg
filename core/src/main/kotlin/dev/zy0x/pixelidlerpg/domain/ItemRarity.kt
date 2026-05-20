package dev.zy0x.pixelidlerpg.domain

/**
 * Item rarity tiers. Drop weight decreases from Common to Legendary.
 */
enum class ItemRarity(
    val displayName: String,
    val dropWeight: Float,
    val colorHex: String,
) {
    COMMON    ("Common",    weight = 60f,  colorHex = "#aaaaaa"),
    UNCOMMON  ("Uncommon",  weight = 25f,  colorHex = "#44cc44"),
    RARE      ("Rare",      weight = 10f,  colorHex = "#4488ff"),
    EPIC      ("Epic",      weight = 4f,   colorHex = "#aa44ff"),
    LEGENDARY ("Legendary", weight = 1f,   colorHex = "#ffaa00"),
}
