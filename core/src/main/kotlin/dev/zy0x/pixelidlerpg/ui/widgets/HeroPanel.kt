package dev.zy0x.pixelidlerpg.ui.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import dev.zy0x.pixelidlerpg.assets.AssetDescriptors
import dev.zy0x.pixelidlerpg.assets.GameAssetManager
import dev.zy0x.pixelidlerpg.ui.UiSkin

/**
 * HeroPanel — small portrait + HP bar for one hero slot.
 *
 * Portrait atlas region: "heroes/<classKey>_portrait"
 * Falls back to a solid color placeholder if region not found.
 *
 * On KO: panel fades to 40% opacity + grey HP bar.
 * On revive (isDead=false after isDead=true): fade back to 100%.
 */
class HeroPanel(
    assets: GameAssetManager,
    classKey: String,
) : Group() {

    companion object {
        const val WIDTH  = 80f
        const val HEIGHT = 52f
    }

    private val hpBar = ProgressBar(0f, 1f, 0.01f, false, UiSkin.hpBarStyle(isBoss = false))
    private var wasDead = false

    init {
        setSize(WIDTH, HEIGHT)

        // Portrait image
        val heroAtlas  = assets.asset(AssetDescriptors.HEROES)
        val region     = heroAtlas.findRegion("${classKey}_portrait")
        val portrait   = if (region != null) Image(TextureRegionDrawable(region))
                         else Image(UiSkin.placeholderDrawable(Color.GRAY))
        portrait.setSize(WIDTH, HEIGHT - 14f)
        portrait.setPosition(0f, 14f)
        addActor(portrait)

        // HP bar at bottom of panel
        hpBar.setSize(WIDTH, 10f)
        hpBar.setPosition(0f, 2f)
        addActor(hpBar)
    }

    fun update(currentHp: Int, maxHp: Int, isDead: Boolean) {
        hpBar.value = if (maxHp > 0) currentHp.toFloat() / maxHp else 0f

        if (isDead && !wasDead) {
            addAction(Actions.fadeOut(0.4f))
        } else if (!isDead && wasDead) {
            addAction(Actions.fadeIn(0.4f))
        }
        wasDead = isDead
    }
}
