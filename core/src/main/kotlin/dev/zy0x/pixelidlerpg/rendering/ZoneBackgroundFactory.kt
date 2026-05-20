package dev.zy0x.pixelidlerpg.rendering

import dev.zy0x.pixelidlerpg.assets.AssetDescriptors
import dev.zy0x.pixelidlerpg.assets.GameAssetManager
import dev.zy0x.pixelidlerpg.domain.Zone
import ktx.log.logger

/**
 * Factory that maps a [Zone] enum value to its loaded [ZoneBackground].
 *
 * Only Zone 1 (Verdant Forest) is available in this milestone.
 * Zones 2+ return the Zone 1 background as fallback until their tilemaps are created.
 */
object ZoneBackgroundFactory {
    private val log = logger<ZoneBackgroundFactory>()

    fun create(zone: Zone, assets: GameAssetManager): ZoneBackground {
        val descriptor = when (zone) {
            Zone.VERDANT_FOREST -> AssetDescriptors.ZONE_1_VERDANT
            Zone.DARK_CAVES     -> AssetDescriptors.ZONE_2_CAVES
            else -> {
                log.debug { "No tilemap for $zone yet. Using Zone 1 as fallback." }
                AssetDescriptors.ZONE_1_VERDANT
            }
        }
        return ZoneBackground(assets.asset(descriptor))
    }
}
