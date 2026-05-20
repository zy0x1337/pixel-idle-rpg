package dev.zy0x.pixelidlerpg.domain

/**
 * All available hero classes.
 * Each class defines a display name and a base stat template.
 */
enum class HeroClass(
    val displayName: String,
    val baseStats: Stats,
) {
    WARRIOR(
        displayName = "Warrior",
        baseStats = Stats(maxHp = 120, atk = 12, def = 8, spd = 1.0f, luck = 1.0f)
    ),
    MAGE(
        displayName = "Mage",
        baseStats = Stats(maxHp = 70, atk = 22, def = 3, spd = 0.8f, luck = 1.1f)
    ),
    RANGER(
        displayName = "Ranger",
        baseStats = Stats(maxHp = 90, atk = 16, def = 5, spd = 1.3f, luck = 1.2f)
    ),
    CLERIC(
        displayName = "Cleric",
        baseStats = Stats(maxHp = 80, atk = 10, def = 6, spd = 0.9f, luck = 1.0f)
    ),
    ROGUE(
        displayName = "Rogue",
        baseStats = Stats(maxHp = 85, atk = 18, def = 4, spd = 1.4f, luck = 1.5f)
    ),
}
