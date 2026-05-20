# 🎨 Art Style Guide
## Pixel Idle RPG

---

## Core Aesthetic

> ⚠️ Final setting TBD — this guide uses Fantasy-Medieval as placeholder.

- **Grid size:** 16×16 px base unit (UI elements: 32×32 or 64×64)
- **Color palette:** Limited per-sprite (max 8–12 colors per character/tile)
- **Outline style:** 1px dark outline on all characters and interactive objects
- **Shading:** Flat with 2-tone shading (highlight + shadow), no anti-aliasing
- **Animation:** 3–6 frames for idle/walk/attack cycles

---

## Sprite Specifications

| Asset Type | Size | Frames | Notes |
|---|---|---|---|
| Hero (idle) | 16×16 | 4 | Looping |
| Hero (attack) | 16×16 | 3 | One-shot, return to idle |
| Hero (hurt) | 16×16 | 2 | Flash effect |
| Hero (death) | 16×16 | 4 | Fall + fade |
| Enemy (idle) | 16×16 | 4 | Looping |
| Enemy (attack) | 16×16 | 3 | One-shot |
| Tile (ground) | 16×16 | 1 | Static |
| Tile (decoration) | 16×16 | 1–3 | Grass sway etc. |
| UI icons | 16×16 | 1 | No anti-alias |
| Portraits | 32×32 | 1 | Hero panel close-up |

---

## Color Palette (Draft)

> To be finalized once world setting is confirmed.

**Fantasy-Medieval Palette (Placeholder):**
```
Background / Ground:  #2d1b00, #5c3317, #8b6914, #c8a84b
Vegetation:           #1a3a1a, #2d5a2d, #4a8c4a, #7bc47b
Sky:                  #0d1b2a, #1e3a5f, #4a7fbf, #a8c8f0
UI:                   #1a1a2e, #2d2d44, #4a4a6e, #8888cc
Accent / Gold:        #ffd700, #ffaa00, #cc7700
Danger / Enemy HP:    #cc0000, #ff4444
Hero HP:              #00aa00, #44ff44
```

---

## Tilemap Rules

- All tilemaps created in **Tiled Map Editor** (`.tmx` format)
- Tile size: **16×16 px**
- Layers:
  1. `Ground` — base terrain (walkable)
  2. `Decoration` — non-blocking overlays (grass, flowers)
  3. `Collision` — invisible collision layer
  4. `Objects` — entity spawn points, triggers (Tiled Object Layer)
- Tileset atlas: one `.png` per zone, max 512×512 px

---

## Asset Pipeline

```
Draw in Aseprite (or similar)
  → Export as PNG sprite sheet
  → Pack into Texture Atlas with TexturePacker or libGDX tools
  → Place .atlas + .png in assets/sprites/
  → Reference in AssetManager via KTX
```

---

## Tools

| Tool | Purpose | Free? |
|---|---|---|
| **Aseprite** | Pixel art drawing + animation | Paid / compile free |
| **Tiled** | Tilemap editing | Free |
| **TexturePacker** | Sprite atlas packing | Free (basic) |
| **BFXR / sfxr** | 8-bit SFX generation | Free |
| **BeepBox** | Chiptune BGM creation | Free (web) |
