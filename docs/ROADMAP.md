# 🗺️ Development Roadmap
## Pixel Idle RPG

---

## Phase 0 — Foundation ✅ (Current)
> Repo setup, planning, stack decisions

- [x] GitHub repository created
- [x] Project structure defined
- [x] GDD drafted
- [x] Tech stack confirmed (libGDX + Kotlin + KTX + Fleks)
- [ ] Decide world setting / art direction
- [ ] Initialize libGDX project via gdx-liftoff
- [ ] Set up `develop` branch
- [ ] Configure `.github/workflows` for build CI

---

## Phase 1 — MVP v0.1 🚧
> Goal: Something playable on device. 1 hero, auto-fight, basic loop.

### Engine & Architecture
- [ ] libGDX project via gdx-liftoff (Kotlin + Android + KTX + Fleks)
- [ ] Screen Manager (Main Menu → Game Screen)
- [ ] Game Loop (fixed timestep, delta time)
- [ ] Asset Manager setup (AssetManager + KTX async loading)
- [ ] Basic logging + crash handling

### Gameplay
- [ ] 1 Hero (Warrior) with HP, ATK, DEF
- [ ] Auto-combat system (tick-based)
- [ ] 3 enemy types (Zone 1: Forest)
- [ ] Gold drop system
- [ ] Basic shop (buy ATK/DEF upgrade)
- [ ] Simple save system (libGDX Preferences)

### Art (Placeholder)
- [ ] 16×16 Hero sprite (walk + attack animation)
- [ ] 3× Enemy sprites
- [ ] Basic tilemap (Forest zone, Tiled)
- [ ] UI skin (Scene2D / KTX)

### Milestone: First playable APK on device

---

## Phase 2 — Alpha v0.2 🔒
> Goal: Real game feel. Multiple classes, world map, inventory.

- [ ] 3 hero classes (Warrior, Mage, Ranger)
- [ ] Full tilemap world (2–3 zones)
- [ ] Inventory system (equipment slots + items)
- [ ] Equipment drop system (rarities)
- [ ] Hero panel UI (stats, equipment slots)
- [ ] World map / zone selection screen
- [ ] EXP + Leveling system
- [ ] Animated gold counter
- [ ] Sound: BGM (chiptune) + SFX hits

### Milestone: Shareable screenshot / screen recording

---

## Phase 3 — Beta v0.3 🔒
> Goal: Testable by others. Full loop with endgame.

- [ ] Dungeon system (floors, wave progression)
- [ ] Boss encounters (phase mechanics)
- [ ] Offline progress calculation
- [ ] "Welcome back" summary screen
- [ ] Base building (3 buildings)
- [ ] Prestige system (basic)
- [ ] Active abilities (1 per hero class)
- [ ] 5 hero classes complete
- [ ] Balancing pass on economy
- [ ] Google Play internal testing track

### Milestone: Beta APK distributed for testing

---

## Phase 4 — v1.0 Release 🔒
> Goal: Play Store launch-ready.

- [ ] Full prestige system with permanent upgrades
- [ ] Story: 5 chapters × 3 boss fights
- [ ] All 5 zones complete (art + enemies)
- [ ] Full audio (BGM + SFX + UI sounds)
- [ ] Achievements system
- [ ] Settings screen (audio, graphics, data reset)
- [ ] Play Store listing (screenshots, description, icon)
- [ ] Privacy Policy
- [ ] Performance profiling (target: 60fps on mid-range devices)
- [ ] Crash reporting (Firebase Crashlytics)

### Milestone: Play Store release 🚀

---

## Future (v2.0+) 💡
- Guild system / social features
- PvP leaderboards
- Seasonal events
- New hero classes (Necromancer, Paladin)
- Cloud save (Google Play Games)
