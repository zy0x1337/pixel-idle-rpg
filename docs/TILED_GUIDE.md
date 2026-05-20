# 🗺️ Tiled Map Editor Guide
## Pixel Idle RPG — Zone Creation

---

## Setup

1. Download [Tiled Map Editor](https://www.mapeditor.org/) (free)
2. Open `assets/tilemaps/zone1_verdant_forest.tmx`
3. Import tileset: `assets/tilemaps/tilesets/zone1_tileset.png`

---

## Map Specs

| Setting | Value |
|---|---|
| Orientation | Orthogonal |
| Tile size | 16 × 16 px |
| Map size | 23 × 40 tiles (= 368 × 640 px ≈ virtual screen) |
| Render order | Right-down |

---

## Required Layer Names

The layer names are **hardcoded** in `ZoneBackground.kt` — do not rename them.

| Layer | Render order | Contents |
|---|---|---|
| `background` | Bottom | Sky, far-away trees, clouds |
| `midground` | Middle | Ground tiles, near platforms |
| `foreground` | Top (after sprites) | Overhanging leaves, torches, fog |

---

## Tile Index Reference — Zone 1

| Tile ID | Description | Color hint |
|---|---|---|
| 0 | Empty / transparent | — |
| 1 | Sky (bright) | `#87CEEB` area |
| 2 | Sky mid-gradient | |
| 3 | Sky lower | |
| 4 | Far tree silhouette (dark green) | |
| 5 | Near foliage | |
| 6 | Grass top | |
| 7 | Dirt fill | |

---

## Workflow: Add a New Zone

1. **Duplicate** `zone1_verdant_forest.tmx` → rename to `zone2_dark_caves.tmx`
2. **Create tileset** in Nano Banana 2, export `zone2_tileset.png` to `assets/tilemaps/tilesets/`
3. **Create** `zone2_tileset.tsx` referencing the new PNG
4. **Paint** the map in Tiled
5. **Add** `AssetDescriptors.ZONE_2_CAVES` (already defined) to `GameAssetManager.queueAll()`
6. **Update** `ZoneBackgroundFactory` to map `Zone.DARK_CAVES` to the new descriptor
7. Commit to a new `art/tilemap-zone2` branch

---

## Zone Design Guidelines

| Zone | Mood | Sky | Ground | Accent |
|---|---|---|---|---|
| 1 Verdant Forest | Cheerful, bright | Light blue | Grass + dark soil | Green leaves |
| 2 Dark Caves | Tense, dark | Pitch black | Grey stone | Glowing crystal blue |
| 3 Ruined Castle | Haunted, moody | Purple dusk | Cracked stone | Torchlight orange |
| 4 Magma Depths | Intense, danger | Dark red haze | Black rock + lava | Ember glow |
| 5 The Void | Eerie, abstract | Deep space | Floating obsidian | Neon purple |
