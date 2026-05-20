# 📋 Game Design Document (GDD)
## Pixel Idle RPG

**Version:** 0.1 (Draft)  
**Last Updated:** 2026-05-20  
**Status:** Planning Phase

---

## 1. Game Overview

| Field | Value |
|---|---|
| **Genre** | Idle RPG / Clicker RPG |
| **Platform** | Android (API 26+) |
| **Art Style** | Pixel Art (16×16 / 32×32 sprites) |
| **Camera** | 2D Top-down / Side-scrolling (TBD) |
| **Session Length** | 2–10 min active, indefinitely idle |
| **Target Audience** | Casual to mid-core RPG fans, ages 14+ |

### Elevator Pitch
> Build a party of pixel heroes that fight automatically and grow stronger — even while you're away. Come back to loot, upgrade your base, and push deeper into dangerous dungeons.

---

## 2. Core Game Loops

### 🔁 Idle Loop (Passive)
```
Heroes fight automatically
  → Earn Gold + EXP
  → Heroes level up → stronger enemies unlocked
  → Spend Gold on upgrades
  → Repeat
```

### ⚔️ Active Loop (Player-driven)
```
Player selects Dungeon / Zone
  → Manually trigger special abilities
  → Boss fights (timing-based mechanics)
  → Rare item drops → Equipment → Build optimization
```

### 🏆 Meta Loop (Long-term Progression)
```
Reach prestige threshold
  → Prestige: reset progress, gain permanent multipliers
  → Unlock new hero classes
  → Advance story chapters
  → New zones and world areas open
```

---

## 3. World & Setting

> ⚠️ **TBD — Awaiting design decision:**
> Fantasy-Medieval, Sci-Fi Dungeon, Dark Fantasy, or Cozy Village RPG.

**Placeholder:** Fantasy-Medieval (Dungeons, Magic, Dragons)

### Zones (Draft)
| # | Zone Name | Enemy Type | Difficulty |
|---|---|---|---|
| 1 | Verdant Forest | Slimes, Wolves | Starter |
| 2 | Dark Caves | Bats, Goblins | Easy |
| 3 | Ruined Castle | Skeletons, Knights | Medium |
| 4 | Magma Depths | Fire Elementals, Demons | Hard |
| 5 | The Void | Void Creatures, Bosses | End-game |

---

## 4. Hero Classes

> ⚠️ **TBD — Final class names & abilities pending art direction decision.**

### Draft Classes

| Class | Role | Primary Stat | Passive Ability |
|---|---|---|---|
| **Warrior** | Tank/DPS | STR | Thorns — reflects % damage |
| **Ranger** | DPS/Support | AGI | Volley — multi-target auto-attack |
| **Mage** | Burst DPS | INT | Arcane Burst — bonus vs. boss enemies |
| **Cleric** | Healer | WIS | Regen Aura — passive HP regen for party |
| **Rogue** | DPS/Utility | AGI | Backstab — % chance for double gold |

### Hero Stats
- **HP** — Total health pool
- **ATK** — Base attack damage per second
- **DEF** — Damage reduction flat value
- **SPD** — Attack speed multiplier
- **LUCK** — Drop rate bonus multiplier

---

## 5. Combat System

### Auto-Combat
- Runs on a fixed tick rate (e.g. every 1.0s base)
- Heroes attack enemies in order (aggro / priority system TBD)
- Damage = (ATK × multipliers) - enemy DEF
- Death → enemy drops loot, next enemy spawns

### Active Abilities
- Each hero has 1 active ability (player-triggered)
- Cooldown-based (e.g. 30s)
- Examples: Warrior Shield Bash (stun), Mage Fireball (AoE), Rogue Shadow Step (dodge)

### Boss Encounters
- Appear every N enemies (e.g. every 10)
- Have special mechanics (e.g. rage timer, phase 2 above 50% HP)
- Drop guaranteed rare loot + story progress on first kill

---

## 6. Progression Systems

### 6.1 Hero Leveling
- EXP from combat → Level up → stat increases
- Level cap: 100 (resets to 1 with bonus on prestige)
- Level milestones unlock new abilities or passive upgrades

### 6.2 Equipment
- Slots: Weapon, Armor, Accessory (×2)
- Rarities: Common → Uncommon → Rare → Epic → Legendary
- Equipment found via dungeon drops or crafted

### 6.3 Base Building
- Player owns a Home Base (e.g. village, guild hall)
- Buildings: Barracks (hero capacity), Smithy (crafting), Tavern (hero recruitment)
- Buildings upgrade with Gold + special resources

### 6.4 Prestige
- Triggered manually by player once threshold is met
- Resets: Hero levels, Gold, Zone progress
- Keeps: Equipment, Prestige currency, unlocked classes
- Grants: Permanent stat multipliers (e.g. +5% gold/run)

---

## 7. Economy

| Currency | How Earned | How Spent |
|---|---|---|
| **Gold** | Combat drops, idle farming | Shop, upgrades, building |
| **Gems** | Boss kills, achievements | Premium shop (no pay-to-win) |
| **Prestige Shards** | Prestige runs | Permanent upgrades, class unlocks |
| **Crafting Materials** | Zone-specific drops | Equipment crafting |

---

## 8. Offline Progress

- Game calculates time delta since last session
- Simulates idle combat results (gold, EXP) for up to 8h offline
- Player sees a "Welcome back!" summary screen
- Offline efficiency: 50% of online rate (tunable)

---

## 9. UI / UX Overview

### Screens
| Screen | Description |
|---|---|
| **Main Menu** | Logo, Start, Continue, Settings |
| **Game Screen** | Battle viewport + HUD |
| **Hero Panel** | Party management, stats, equipment |
| **Map/World** | Zone selection |
| **Base** | Building upgrades |
| **Shop** | Buy items, upgrades |
| **Prestige** | Prestige confirmation + rewards preview |
| **Settings** | Audio, graphics, data management |

### HUD Elements
- Party HP bars (compact, top area)
- Active enemy HP + name
- Gold counter (animated on pick-up)
- Zone/wave indicator
- Active ability buttons (bottom row)

---

## 10. Audio

| Type | Style | Notes |
|---|---|---|
| **BGM** | Chiptune / LoFi Pixel | Loop-based, per zone |
| **SFX** | 8-bit attack/hit/level-up | Short, punchy |
| **UI Sounds** | Soft click, coin jingle | Subtle |

---

## 11. Open Questions

- [ ] Final world setting / art direction (Fantasy vs. Sci-Fi vs. Cozy)
- [ ] Camera perspective: top-down vs. side-scrolling combat view
- [ ] Multiplayer features? (guild system, leaderboards) — likely v2.0
- [ ] Monetization model: free + cosmetic IAP? ads? premium?
- [ ] Story depth: light narrative or full story chapters?
