# game.map — Tilemap System

## Files

| File | Responsibility |
|---|---|
| `TiledMapLoader.kt` | AssetManager wrapper; loads `.tmx`, returns `OrthogonalTiledMapRenderer` |
| `MapObjectSpawner.kt` | Parses the `objects` ObjectLayer → typed `ZoneSpawnData` |
| `BackgroundRenderer.kt` | 3-layer parallax background with configurable scroll speeds |
| `ZoneData.kt` | Enum of all zones with paths, music, boss wave numbers |
| `ZoneTransitionSystem.kt` | Ashley system that detects hero entering `zone_exit` rect |

## Render Order in GameScreen

```kotlin
// 1. Background parallax
batch.begin()
backgroundRenderer.render(batch, camera)
batch.end()

// 2. Tilemap — behind sprites
tiledMapRenderer.setView(camera)
tiledMapRenderer.render(intArrayOf(LAYER_BACKGROUND, LAYER_FLOOR, LAYER_WALLS))

// 3. Sprites (y-sorted via Ashley)
batch.begin()
entityRenderSystem.update(delta)
batch.end()

// 4. Tilemap — foreground (in front of sprites)
tiledMapRenderer.render(intArrayOf(LAYER_FOREGROUND))

// 5. HUD
hudStage.act(delta)
hudStage.draw()
```

## Tiled Setup Checklist

```
Map:     25 × 40 tiles, 16×16 px each, Orthogonal, CSV format
Layers (top→bottom):
  [Object] objects      ← spawn points + triggers
  [Tile]   foreground   ← index 3, renders in front of sprites
  [Tile]   walls        ← index 2
  [Tile]   floor        ← index 1
  [Tile]   background   ← index 0

Object names:
  hero_spawn        Point
  enemy_spawn_01    Point   (increment for more)
  boss_spawn        Point
  zone_exit         Rectangle
  camera_bounds     Rectangle
```
