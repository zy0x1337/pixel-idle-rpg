# 🎨 Asset Pipeline
## Pixel Idle RPG

---

## Toolchain

| Tool | Purpose |
|---|---|
| **Nano Banana 2** | Primary pixel-art editor — create all sprites, animations, items |
| **gdx-tools TexturePacker** | Pack individual PNGs into a TextureAtlas (.atlas + .png sheet) |
| **Tiled Map Editor** | Create zone tilemaps (.tmx) |
| **Audacity / BFXR** | Chiptune SFX + music (future) |

---

## Naming Convention

All sprite frames exported from Nano Banana 2 must follow this exact pattern:

```
<entity>_<action>_<frame_number_2digits>.png
```

### Examples
```
warrior_idle_00.png
warrior_idle_01.png
warrior_attack_00.png
warrior_attack_01.png
warrior_attack_02.png
warrior_hurt_00.png
warrior_dead_00.png
warrior_dead_01.png

slime_idle_00.png
slime_idle_01.png
slime_dead_00.png

goblin_scout_idle_00.png
goblin_scout_attack_00.png
```

### Valid Action Names

| Action | Frames (target) | Notes |
|---|---|---|
| `idle` | 4–6 | Looping breathing / float animation |
| `walk` | 4–6 | Looping (heroes moving to next zone) |
| `attack` | 3–4 | One-shot, returns to idle |
| `skill` | 4–5 | One-shot, unique to each class |
| `hurt` | 2 | One-shot flash |
| `dead` | 3–4 | One-shot, holds last frame |
| `victory` | 4 | Looping (end of wave) |

---

## Atlas Structure

After export, group PNGs into folders and pack with gdx-tools:

```
assets/
  sprites/
    src/                        ← Raw PNGs from Nano Banana 2
      heroes/
        warrior_idle_00.png
        warrior_idle_01.png
        ...
      enemies/
        slime_idle_00.png
        ...
      ui/
        hp_bar_fill.png
        hp_bar_bg.png
        gold_icon.png
        ...
      items/
        sword_common.png
        sword_rare.png
        ...
      vfx/
        hit_spark_00.png
        levelup_00.png
        ...
    atlas/                      ← Packed output (committed to repo)
      heroes.atlas
      heroes.png
      enemies.atlas
      enemies.png
      ui.atlas
      ui.png
      items.atlas
      items.png
      vfx.atlas
      vfx.png
```

---

## Sprite Specs

| Entity type | Canvas size | Scale factor | Notes |
|---|---|---|---|
| Heroes | 16×16 px | ×3 in-engine | Displayed as 48×48 px |
| Enemies (normal) | 16×16 px | ×3 in-engine | |
| Enemies (boss) | 24×24 px | ×3 in-engine | Displayed as 72×72 px |
| UI icons / items | 16×16 px | ×2 in-engine | |
| Tileset tiles | 16×16 px | ×1 in-engine | Tiled renders at native size |

**Color palette:** Max 16 colors per sprite (classic RPG constraint). Suggested: [Lospec DB16](https://lospec.com/palette-list/db16) or similar.

---

## Hero Sprites Needed (Priority Order)

### Phase 1 — MVP
- [ ] `warrior` — idle, attack, hurt, dead
- [ ] `mage` — idle, attack, hurt, dead
- [ ] `archer` — idle, attack, hurt, dead

### Phase 2
- [ ] `rogue` — all actions
- [ ] `paladin` — all actions

---

## Enemy Sprites Needed (Priority Order)

### Zone 1 — Verdant Forest
- [ ] `slime` — idle, attack, dead
- [ ] `forest_wolf` — idle, attack, hurt, dead
- [ ] `ancient_treant` (boss) — idle, attack, dead

### Zone 2 — Dark Caves
- [ ] `cave_bat` — idle, attack, dead
- [ ] `goblin_scout` — idle, attack, dead
- [ ] `goblin_warchief` (boss) — idle, attack, dead

---

## Item Icons Needed

### Weapons (16×16)
- [ ] `sword_common`, `sword_uncommon`, `sword_rare`, `sword_epic`, `sword_legendary`
- [ ] `bow_common` … `bow_legendary`
- [ ] `staff_common` … `staff_legendary`
- [ ] `dagger_common` … `dagger_legendary`

### Armor (16×16)
- [ ] `shield_common` … `shield_legendary`
- [ ] `plate_common` … `plate_legendary`
- [ ] `robe_common` … `robe_legendary`

### Accessories (16×16)
- [ ] `ring_common` … `ring_legendary`
- [ ] `amulet_common` … `amulet_legendary`

---

## UI Sprites Needed
- [ ] HP bar background + fill
- [ ] EXP bar background + fill
- [ ] Gold coin icon
- [ ] Ability button frame (normal, pressed)
- [ ] Item rarity frame borders (Common=grey, Uncommon=green, Rare=blue, Epic=purple, Legendary=gold)
- [ ] Boss health bar (wider variant)

---

## VFX Sprites Needed
- [ ] `hit_spark` (3 frames) — small white/yellow flash on hit
- [ ] `levelup` (4 frames) — golden burst around hero
- [ ] `gold_pop` (3 frames) — coin spin + fade
- [ ] `boss_death` (5 frames) — dramatic explosion

---

## Workflow: Nano Banana 2 → Repo

1. **Create sprite in Nano Banana 2**
   - Set canvas to 16×16 px (or 24×24 for bosses)
   - Animate frames using Nano Banana 2’s timeline
   - Export all frames as individual PNGs to `assets/sprites/src/<category>/`

2. **Pack with gdx-tools TexturePacker**
   ```bash
   # From repo root
   java -cp gdx-tools.jar com.badlogic.gdx.tools.texturepacker.TexturePacker \
     assets/sprites/src/heroes \
     assets/sprites/atlas \
     heroes
   ```

3. **Commit both atlas + sheet PNG**
   ```bash
   git add assets/sprites/
   git commit -m "art: add warrior idle + attack sprites"
   git push
   ```

4. **Game auto-loads** via `GameAssetManager.queueAll()` — no code changes needed.
