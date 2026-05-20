package dev.zy0x.pixelidlerpg.rendering.animation

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.log.logger

/**
 * AnimationController — manages state-machine animation for a single sprite entity.
 *
 * On construction, pre-builds an [Animation] per [SpriteAnimation] state from
 * the TextureAtlas regions matching the pattern "<prefix>_<state.atlasKey>".
 *
 * If a state has no frames in the atlas (placeholder / missing asset),
 * it falls back to the IDLE animation. This allows development to continue
 * before all Nano Banana 2 exports are complete.
 *
 * @param atlas   The TextureAtlas to source frames from
 * @param prefix  Entity-specific prefix (e.g. "warrior", "slime")
 */
class AnimationController(
    private val atlas: TextureAtlas,
    private val prefix: String,
) {
    companion object {
        private val log = logger<AnimationController>()
    }

    private val animations: Map<SpriteAnimation, Animation<TextureRegion>>
    private var currentState = SpriteAnimation.IDLE
    private var stateTime = 0f
    private var previousState = SpriteAnimation.IDLE

    init {
        animations = SpriteAnimation.entries.associateWith { state ->
            val regionKey = "${prefix}_${state.atlasKey}"
            val frames = atlas.findRegions(regionKey)
            if (frames.isEmpty) {
                // Fallback: use any region named "<prefix>_idle" or the first region in the atlas
                log.debug { "No frames for '$regionKey' in atlas. Using fallback." }
                val fallback = atlas.findRegions("${prefix}_idle").takeIf { !it.isEmpty }
                    ?: com.badlogic.gdx.utils.Array<TextureRegion>().also { arr ->
                        atlas.regions.firstOrNull()?.let { arr.add(it) }
                    }
                Animation(state.frameDuration, fallback,
                    if (state.looping) Animation.PlayMode.LOOP else Animation.PlayMode.NORMAL)
            } else {
                Animation(state.frameDuration, frames,
                    if (state.looping) Animation.PlayMode.LOOP else Animation.PlayMode.NORMAL)
            }
        }
        log.debug { "AnimationController ready for '$prefix'" }
    }

    /**
     * Set the current animation state. Resets [stateTime] if state changed.
     * For non-looping animations (ATTACK, HURT, DEAD), the controller will
     * automatically return to IDLE when the animation finishes.
     */
    fun setState(state: SpriteAnimation) {
        if (state == currentState) return
        previousState = currentState
        currentState  = state
        stateTime     = 0f
    }

    /**
     * Advance the timer and return the correct [TextureRegion] for this frame.
     *
     * @param delta Frame delta from [com.badlogic.gdx.Gdx.graphics.deltaTime]
     */
    fun currentFrame(delta: Float): TextureRegion {
        stateTime += delta
        val anim = animations[currentState]!!

        // Auto-return to IDLE when a non-looping animation finishes
        if (!currentState.looping && anim.isAnimationFinished(stateTime)) {
            currentState = SpriteAnimation.IDLE
            stateTime    = 0f
        }

        return anim.getKeyFrame(stateTime)
    }

    /** Trigger a one-shot animation that returns to IDLE when done. */
    fun playOnce(state: SpriteAnimation) = setState(state)

    val isFinished: Boolean
        get() = !currentState.looping && animations[currentState]!!.isAnimationFinished(stateTime)
}
