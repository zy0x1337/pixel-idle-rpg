<?xml version="1.0" encoding="UTF-8"?>
<!--
  Tileset for Zone 1 — Verdant Forest
  =====================================
  PLACEHOLDER — replace image source with real Nano Banana 2 export.

  Tile IDs used in zone1_verdant_forest.tmx:
    0  = empty (transparent)
    1  = sky (light blue)
    2  = sky gradient mid
    3  = sky gradient lower
    4  = far trees (dark green silhouette)
    5  = midground foliage
    6  = grass top
    7  = dirt/earth fill

  To replace:
    1. Draw 16x16 tiles in Nano Banana 2, export as zone1_tileset.png
    2. Place file at assets/tilemaps/tilesets/zone1_tileset.png
    3. Update the <image source="..."> tag below
    4. Update tile IDs in zone1_verdant_forest.tmx as needed
-->
<tileset version="1.10" tiledversion="1.10.2"
         name="zone1_tileset" tilewidth="16" tileheight="16"
         spacing="0" margin="0" tilecount="16" columns="8">
  <image source="zone1_tileset.png" width="128" height="32"/>
</tileset>
