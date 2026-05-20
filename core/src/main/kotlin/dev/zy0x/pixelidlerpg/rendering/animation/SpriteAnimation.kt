package dev.zy0x.pixelidlerpg.rendering.animation

/**
 * Animation states used across all characters.
 *
 * Each state maps to a set of atlas regions following the naming convention:
 *   <prefix>_<state.atlasKey>_<frame_number>
 *
 * Example (warrior, ATTACK):
 *   regions: warrior_attack_00, warrior_attack_01, warrior_attack_02
 *
 * Art export checklist (Nano Banana 2):
 *   - Export all frames as individual PNGs: <entity>_<action>_<NN>.png
 *   - Pack into TextureAtlas with gdx-tools TexturePacker
 *   - Place .atlas + .png sheet in assets/sprites/atlas/
 */
enum class SpriteAnimation(
    val atlasKey: String,
    val frameDuration: Float,
    val looping: Boolean,
) {
    IDLE   (atlasKey = "idle",   frameDuration = 0.20f, looping = true),
    WALK   (atlasKey = "walk",   frameDuration = 0.12f, looping = true),
    ATTACK (atlasKey = "attack", frameDuration = 0.08f, looping = false),
    HURT   (atlasKey = "hurt",   frameDuration = 0.10f, looping = false),
    DEAD   (atlasKey = "dead",   frameDuration = 0.15f, looping = false),
    SKILL  (atlasKey = "skill",  frameDuration = 0.08f, looping = false),
    VICTORY(atlasKey = "victory",frameDuration = 0.18f, looping = true),
}
