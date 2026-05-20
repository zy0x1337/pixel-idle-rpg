# 🎮 Pixel Idle RPG

> A pixel-art style idle RPG for Android — built with libGDX, Kotlin, KTX & Fleks ECS.

![Platform](https://img.shields.io/badge/platform-Android-green?style=flat-square)
![Language](https://img.shields.io/badge/language-Kotlin-purple?style=flat-square)
![Framework](https://img.shields.io/badge/framework-libGDX-red?style=flat-square)
![Status](https://img.shields.io/badge/status-In%20Development-yellow?style=flat-square)

---

## 📖 About

Pixel Idle RPG is an Android game where the player builds and manages a party of pixel-art heroes that automatically fight, loot and level up — even while offline. The player can actively influence combat, manage equipment, upgrade a home base, and unlock new hero classes through a prestige system.

---

## 🛠️ Tech Stack

| Library | Purpose | Repo |
|---|---|---|
| **libGDX** | Core game framework (rendering, input, audio) | [github.com/libgdx/libgdx](https://github.com/libgdx/libgdx) |
| **KTX** | Kotlin extensions for libGDX | [github.com/libktx/ktx](https://github.com/libktx/ktx) |
| **Fleks ECS** | Entity Component System | [github.com/Quillraven/Fleks](https://github.com/Quillraven/Fleks) |
| **gdx-liftoff** | Project setup & Gradle generator | [github.com/libgdx/gdx-liftoff](https://github.com/libgdx/gdx-liftoff) |
| **kotlinx.serialization** | JSON-based save system | JetBrains |
| **Tiled** | Tilemap editor (pixel maps) | mapeditor.org |

---

## 📁 Project Structure

```
pixel-idle-rpg/
├── core/                          ← Platform-independent game logic
│   └── src/main/kotlin/
│       └── game/
│           ├── screens/           ← MainMenuScreen, GameScreen, UIScreen
│           ├── systems/           ← IdleSystem, CombatSystem, LootSystem
│           ├── entities/          ← Hero, Enemy, Building components
│           ├── data/              ← GameData, SaveState, ItemDatabase
│           └── ui/                ← HUD, Dialogs, Panels (Scene2D / KTX)
├── android/                       ← Android launcher + AndroidManifest
├── assets/                        ← All game assets
│   ├── sprites/                   ← Pixel-art sprite sheets
│   ├── tilemaps/                  ← Tiled .tmx map files
│   ├── ui/                        ← UI skin, fonts, icons
│   └── sounds/                    ← Music & SFX
├── docs/                          ← Game Design Document & planning
│   ├── GDD.md                     ← Full Game Design Document
│   ├── ROADMAP.md                 ← Development milestones
│   └── ART_STYLE.md               ← Pixel art guidelines
├── .github/
│   └── workflows/                 ← CI: Lint + Build checks
├── .gitignore
└── README.md
```

---

## 🌿 Branching Strategy

```
main         ← Always stable. Merge via PR only.
develop      ← Active development branch.
feature/xxx  ← New features (e.g. feature/combat-system)
fix/xxx      ← Bug fixes
art/xxx      ← Asset updates
```

**Commit convention:** `feat:`, `fix:`, `art:`, `docs:`, `refactor:`, `chore:`

---

## 📌 Development Phases

| Phase | Milestone | Key Features |
|---|---|---|
| **MVP v0.1** | Playable prototype | 1 hero, auto-combat, gold farming, basic shop |
| **Alpha v0.2** | First screenshots | 3 classes, tilemap world, inventory |
| **Beta v0.3** | Testable build | Dungeons, bosses, offline progress |
| **v1.0** | Play Store ready | Prestige, story, sounds, full polish |

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17+
- Android SDK (API 26+)
- [Tiled Map Editor](https://www.mapeditor.org/) (for map editing)

### Setup
1. Clone the repo: `git clone https://github.com/zy0x1337/pixel-idle-rpg.git`
2. Open in Android Studio
3. Sync Gradle
4. Run on emulator or Android device (API 26+)

---

## 📄 License

MIT — see [LICENSE](LICENSE)
