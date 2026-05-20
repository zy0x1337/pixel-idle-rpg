package dev.zy0x.pixelidlerpg.ecs.systems

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import dev.zy0x.pixelidlerpg.domain.Item
import dev.zy0x.pixelidlerpg.domain.ItemRarity
import dev.zy0x.pixelidlerpg.domain.ItemSlot
import dev.zy0x.pixelidlerpg.ecs.components.EnemyComponent
import ktx.log.logger
import kotlin.random.Random

/**
 * LootSystem — runs after an enemy dies and resolves item drops.
 *
 * Uses weighted random selection based on [ItemRarity.dropWeight].
 * Bosses always drop at minimum an Uncommon item.
 */
class LootSystem(
    private val zoneState: ZoneState,
    private val onItemDropped: (Item) -> Unit = {},
) : IteratingSystem(
    family = com.github.quillraven.fleks.family { all(EnemyComponent) }
) {
    companion object {
        private val log = logger<LootSystem>()
        private const val BASE_DROP_CHANCE = 0.25f  // 25% base chance for any drop
        private const val BOSS_DROP_CHANCE = 1.0f   // bosses always drop something
    }

    override fun onTickEntity(entity: Entity) {
        val enemy = entity[EnemyComponent]
        if (enemy.isAlive) return // only process dead enemies

        val dropChance = if (enemy.isBoss) BOSS_DROP_CHANCE else BASE_DROP_CHANCE
        if (Random.nextFloat() > dropChance) {
            world.remove(entity)
            return
        }

        val rarity = rollRarity(enemy.isBoss)
        val item = generateItem(rarity, enemy.isBoss)

        onItemDropped(item)
        log.info { "Loot drop: [${rarity.displayName}] ${item.name}" }

        world.remove(entity)
    }

    /** Weighted random rarity roll. Bosses can't drop Common. */
    private fun rollRarity(isBoss: Boolean): ItemRarity {
        val pool = if (isBoss)
            ItemRarity.entries.filter { it != ItemRarity.COMMON }
        else
            ItemRarity.entries.toList()

        val totalWeight = pool.sumOf { it.dropWeight.toDouble() }.toFloat()
        var roll = Random.nextFloat() * totalWeight
        return pool.first { rarity ->
            roll -= rarity.dropWeight
            roll <= 0f
        }
    }

    /** Generates a randomised item for the given rarity. Placeholder names — content DB TBD. */
    private fun generateItem(rarity: ItemRarity, isBoss: Boolean): Item {
        val slot = ItemSlot.entries.random()
        val rarityMult = when (rarity) {
            ItemRarity.COMMON    -> 1
            ItemRarity.UNCOMMON  -> 2
            ItemRarity.RARE      -> 4
            ItemRarity.EPIC      -> 8
            ItemRarity.LEGENDARY -> 16
        }
        return Item(
            id     = "drop_${System.currentTimeMillis()}",
            name   = "${rarity.displayName} ${slotName(slot)}",
            slot   = slot,
            rarity = rarity,
            atkBonus = if (slot == ItemSlot.WEAPON) rarityMult * 3 else 0,
            defBonus = if (slot == ItemSlot.ARMOR)  rarityMult * 2 else 0,
            hpBonus  = if (slot == ItemSlot.ACCESSORY) rarityMult * 10 else 0,
        )
    }

    private fun slotName(slot: ItemSlot) = when (slot) {
        ItemSlot.WEAPON    -> listOf("Sword", "Bow", "Staff", "Dagger").random()
        ItemSlot.ARMOR     -> listOf("Shield", "Plate", "Robe", "Vest").random()
        ItemSlot.ACCESSORY -> listOf("Ring", "Amulet", "Charm", "Rune").random()
    }
}
