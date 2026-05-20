# 🎨 Asset Prompts & Reference
## Pixel Idle RPG — AI-Generated Sprite References

Diese Datei dokumentiert alle generierten Referenz-Bilder und deren Ziel-Dateipfade im Repo.

---

## Warrior — Komplett

### warrior_idle (4 Frames)
- **Referenz:** `docs/refs/warrior_idle_sheet.png`
- **Ziel-Frames:** `assets/sprites/src/heroes/warrior_idle_00.png` bis `warrior_idle_03.png`
- **Canvas:** 16×16 px je Frame
- **Anleitung:** Sheet in Nano Banana 2 öffnen, jeden Frame einzeln auf 16×16 zuschneiden und als `warrior_idle_00.png` etc. exportieren.

### warrior_attack (3 Frames)
- **Referenz:** `docs/refs/warrior_attack_sheet.png`
- **Ziel-Frames:** `assets/sprites/src/heroes/warrior_attack_00.png` bis `warrior_attack_02.png`
- **Canvas:** 16×16 px je Frame

### warrior_hurt (2 Frames) + warrior_dead (3 Frames)
- **Referenz:** `docs/refs/warrior_hurt_dead_sheet.png`
- **Ziel-Frames:**
  - `warrior_hurt_00.png`, `warrior_hurt_01.png`
  - `warrior_dead_00.png`, `warrior_dead_01.png`, `warrior_dead_02.png`
- **Canvas:** 16×16 px je Frame
- **Anleitung:** Die ersten 2 Frames = HURT, letzten 3 Frames = DEAD.

### warrior_portrait
- **Referenz:** `docs/refs/warrior_portrait.png`
- **Ziel:** `assets/sprites/src/heroes/warrior_portrait.png`
- **Canvas:** 80×38 px

---

## Atlas packen (nach Export aus Nano Banana 2)

```bash
# Von Repo-Root aus:
java -cp gdx-tools.jar com.badlogic.gdx.tools.texturepacker.TexturePacker \
  assets/sprites/src/heroes \
  assets/sprites/atlas \
  heroes
```

Das erzeugt:
- `assets/sprites/atlas/heroes.atlas`
- `assets/sprites/atlas/heroes.png`

Beide Dateien committen:
```bash
git add assets/sprites/
git commit -m "art: add warrior sprites + packed atlas"
git push origin art/warrior-sprites
```

---

## Namenskonvention Erinnerung

```
<entity>_<action>_<frame_2digits>.png

warrior_idle_00.png
warrior_attack_01.png
warrior_dead_02.png
warrior_portrait.png   ← kein Frame-Suffix
```
