package dev.zy0x.pixelidlerpg.ui.widgets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import dev.zy0x.pixelidlerpg.assets.GameAssetManager
import dev.zy0x.pixelidlerpg.ui.UiSkin

/**
 * EnemyPanel — enemy name label + HP bar.
 *
 * Normal enemies: standard red HP bar (WIDTH=160)
 * Bosses: wider gold-bordered bar (WIDTH=220), pulsing name label (TODO: add Action)
 */
class EnemyPanel(assets: GameAssetManager) : Group() {

    companion object {
        const val WIDTH = 220f
        const val HEIGHT = 28f
    }

    private val nameLabel = Label("", UiSkin.labelStyle(Color.WHITE))
    private val hpBar     = ProgressBar(0f, 1f, 0.01f, false, UiSkin.hpBarStyle(isBoss = false))
    private var isBoss    = false

    init {
        setSize(WIDTH, HEIGHT)

        nameLabel.setPosition(0f, HEIGHT - 14f)
        addActor(nameLabel)

        hpBar.setSize(WIDTH, 10f)
        hpBar.setPosition(0f, 2f)
        addActor(hpBar)
    }

    fun update(currentHp: Int, maxHp: Int, name: String, boss: Boolean) {
        isBoss = boss
        nameLabel.setText(if (boss) "★ $name ★" else name)
        nameLabel.color = if (boss) Color.GOLD else Color.WHITE
        hpBar.value = if (maxHp > 0) currentHp.toFloat() / maxHp else 0f
    }
}
