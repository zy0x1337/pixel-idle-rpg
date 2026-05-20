# 🗺️ Development Roadmap
## Pixel Idle RPG

---

## Phase 0 — Foundation ✅
> Repo setup, planning, stack decisions

- [x] GitHub repository created
- [x] Project structure defined
- [x] GDD drafted
- [x] Tech stack confirmed (libGDX 1.14.1 + Kotlin + KTX + Fleks)
- [ ] Decide world setting / art direction
- [x] Initialize libGDX project structure (gdx-liftoff equivalent)
- [x] Set up `develop` branch
- [x] Configure `.github/workflows` for build CI

---

## Phase 1 — MVP v0.1 🚧
> Goal: Something playable on device. 1 hero, auto-fight, basic loop.

### Engine & Architecture
- [x] libGDX project: core / android / lwjgl3 modules
- [x] Screen Manager (KtxGame: Loading → MainMenu → Game)
- [x] Asset Manager setup skeleton (AssetManager via KTX)
- [x] Basic logging (KTX logger)
- [ ] Gradle wrapper binary committed (`gradlew` + `gradlew.bat`)

### Domain Models
- [x] Stats, HeroClass (5 classes)
- [x] ItemRarity, Item, ItemSlot
- [x] Zone (5 zones with gold multipliers)

### ECS — Fleks Systems
- [x] `HeroComponent`, `EnemyComponent`, `CombatComponent`
- [x] `SpawnSystem` — spawns enemies, boss every 10 kills
- [x] `CombatSystem` — auto-attack tick, hero vs enemy
- [x] `RewardSystem` — gold + EXP distribution, level-up
- [x] `LootSystem` — weighted rarity drop rolls
- [x] `HeroRespawnSystem` — 5s revive after KO
- [x] `CombatWorld` — assembles full Fleks world from SaveGame

### GameScreen
- [x] Wired `CombatWorld` into `GameScreen.render(delta)`
- [x] Combat + Reward event handlers
- [x] Auto-save on screen hide
- [x] Offline reward calculation on screen show

### Save System
- [x] `SaveGame` + `SavedHero` (kotlinx.serialization)
- [x] `SaveManager` (libGDX Preferences JSON)
- [x] Offline reward calculator (up to 8h, 50% efficiency)

### Gameplay (Remaining for MVP)
- [ ] SpriteBatch rendering (hero + enemy pixel sprites)
- [ ] Tilemap rendering (Zone 1 Tiled map)
- [ ] HUD: HP bars, gold counter, wave indicator
- [ ] Active ability button (1 per hero class)
- [ ] Shop screen (buy ATK/DEF upgrades)
- [ ] First placeholder pixel sprites in assets/

### Milestone: First playable APK on device

---

## Phase 2 — Alpha v0.2 🔒
> Goal: Real game feel. Multiple classes, world map, inventory.

- [ ] Full tilemap world (2–3 zones, Tiled)
- [ ] Inventory system (equipment slots + items)
- [ ] Equipment drop pop-up UI
- [ ] Hero panel (stats, equipment, level bar)
- [ ] World map / zone selection screen
- [ ] Animated gold counter (number tween)
- [ ] Sound: chiptune BGM loop + SFX (hit, level-up, gold)

### Milestone: Shareable screenshot / screen recording

---

## Phase 3 — Beta v0.3 🔒
> Goal: Testable by others. Full loop with endgame.

- [ ] Dungeon system (floor/wave progression)
- [ ] Boss encounter phase mechanics
- [ ] Offline progress — "Welcome back" overlay
- [ ] Base building (3 buildings)
- [ ] Prestige system
- [ ] 5 hero classes fully implemented
- [ ] Economy balancing pass
- [ ] Google Play internal testing track

### Milestone: Beta APK distributed for testing

---

## Phase 4 — v1.0 Release 🔒
> Goal: Play Store launch-ready.

- [ ] Full prestige system with permanent upgrades
- [ ] Story: 5 chapters × 3 boss fights
- [ ] All 5 zones complete
- [ ] Full audio
- [ ] Achievements system
- [ ] Settings screen
- [ ] Play Store listing + Privacy Policy
- [ ] Performance profiling (60fps mid-range)
- [ ] Firebase Crashlytics

### Milestone: Play Store release 🚀

---

## Future (v2.0+) 💡
- Guild system / social features
- PvP leaderboards
- Seasonal events
- New classes (Necromancer, Paladin)
- Cloud save (Google Play Games)
