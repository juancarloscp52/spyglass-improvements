# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/) and this project adheres to [Semantic Versioning](http://semver.org/).

Find your version by looking for the file for your mod-loader:

-   spyglass_improvements-`<mod-version>`+mc`<mc-version>`+forge.jar
-   spyglass-improvements-`<mod-version>`+mc`<mc-version>`+fabric.jar

## [1.5.14] - 2026-7-1 (fabric, neoforge)
Available versions:
-   Fabric: 26.2
-   Neoforge: 26.2

### Changes
-   Port to 26.2

## [1.5.13] - 2026-5-23 (fabric, neoforge)
Available versions:
-   Fabric: 26.1 26.1.1 26.1.2
-   Neoforge: 26.1 26.1.1 26.1.2

### Changes
-   Port to 26.1+
-   Accessories mod has been disabled until it is updated.

## [1.5.12-beta for 1.21.11] - 2025-12-24 (fabric, neoforge)
Available versions:
-   Fabric: 1.21.11
-   Neoforge: 1.21.11

### Changes
-   Port to 1.21.11
-   Accessories mod has been disabled until it is updated.

## [1.5.12] - 2025-11-02 (fabric, neoforge, forge)
Available versions:
-   Fabric: 1.20, 1.20.1, 1.21.9, 1.21.10
-   Forge: 1.20, 1.20.1
-   Neoforge: 1.21.9, 1.21.10

### Changes
-   Added Option to toggle extra zoom.
-   Added Option to force spyglass even if no spyglass is in inventory.
-   Reworked settings screen

## [1.5.11] - 2025-10-28 (fabric, forge)
Available versions:
-   Fabric: 1.20, 1.20.1
-   Forge: 1.20, 1.20.1

### Changes
-   Fix location of settings button.
-   Fix keybindings on forge.

## [1.5.10] - 2025-10-27 (fabric, neoforge, forge)
Available versions:
-   Fabric: 1.20, 1.20.1, 1.21.9, 1.21.10
-   Forge: 1.20, 1.20.1
-   Neoforge: 1.21.9, 1.21.10

### Changes
-   Increased the maximum zoom.

## [1.5.9] - 2025-10-25 (fabric, neoforge)

Available versions:
-   Fabric: 1.21.5, 1.21.6, 1.21.7 ,1.21.8, 1.21.9, 1.21.10
-   NeoForge: 1.21.5, 1.21.6, 1.21.7 ,1.21.8, 1.21.9, 1.21.10

### Changes
-   Use Trinkets Canary for newer trinket compatibility.
-   Fixed Accessories compatibility.

## [1.5.8] - 2025-03-24 (fabric, neoforge)

Available versions:
-   Fabric: 1.21.2, 1.21.3, 1.21.4
-   NeoForge: 1.21.2, 1.21.3, 1.21.4

### Changes
-   Added Accessories Support for both Fabric and Neoforge. Its recommended to use Accessories when possible.
-   Fixed Curios/Trinked support. Spyglass will no longer render on player waist.

## [1.5.7] - 2024-08-16 (fabric, neoforge)

Available versions:
-   Fabric: 1.21, 1.21.1
-   NeoForge: 1.21, 1.21.1

### Fixed
-   Fixed zoom not changing when using the mouse wheel.

## [1.5.6] - 2024-08-12 (fabric, neoforge)

Available versions:
-   Fabric: 1.21, 1.21.1
-   NeoForge: 1.21, 1.21.1

### Changes
-   Added RU_RU translation thanks to Hadeslnc
-   Improved intermod compatibility due to using less invasive mixins.

## [1.5.5] - 2024-08-06 (fabric, neoforge)

Available versions:
-   Fabric: 1.21, 1.21.1
-   NeoForge: 1.21, 1.21.1

### Changes
-   Added ZH_TW translation thanks to yichifauzi

## [1.5.5-forge] - 2024-08-06 (forge)

**IMPORTANT**: This is a special version branched out from forge 1.5(.0) release, since architectury does not work with forge at the moment.
Although functionality-wise is on part with 1.5.4 fabric/neo the codebase is very much different, that's why the clear differentiation.

*Forge is very unstable lately, at least for mod-devs so expect further delays with future versions.*

Available versions:
-   Forge: 1.21

### Changes
-   Port to 1.21
-   Added ZH_TW translation thanks to yichifauzi
-   Added JP_jp translation thanks to EndilCrafter (Same as 1.5.4)

## [1.5.4] - 2024-06-16 (fabric, neoforge)

Available versions:
-   Fabric: 1.21
-   Neoforge: 1.21

### Fixed

-   Fixed Curios and trinkets [Issue #53](https://github.com/juancarloscp52/spyglass-improvements/issues/53)

### Changes
-   Added JP_jp translation thanks to EndilCrafter
-   Updated Neoforge version.

## [1.5.3] - 2024-06-16 (fabric, neoforge)

Available versions:
-   Fabric: 1.20.6
-   Neoforge: 1.20.6

### Changes

-   The codebase has been rewritten to use Architectury in order to easy development of the mod for different loaders.
-   NeoForge support added!
-   Forge support has been temporally paused (hopefully) due to instability and being unable to build for 1.20.6 and up.

## [1.5.2] - 2024-06-01 (fabric)

Available versions:
-   Fabric: 1.20.5, 1.20.6

### Fixed

-   Fabric:
    - Fixed Spyglass settings button position.


## [1.5.1] - 2024-04-27 (fabric)

Available versions:
-   Fabric: 1.18.2, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, 1.20.6

### Fixed

-   Fabric:
    - Fixed Spyglass not toggling while playing on servers in the following cases:
        - Spyglass would not toggle when in creative mode.
        - Spyglass would not toggle when the item is located in the trinket space.*
            - *NOTE:* When using trinkets on multiplayer, Spyglass-Improvements should also be installed on server side.

## [1.5] - 2024-01-31 (forge) - 2024-03-27 (fabric)

Available versions:
-   Fabric, Quilt: 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4
-   Forge: 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4

### Added

-   Forge:
    -   **[Curios API](https://www.curseforge.com/minecraft/mc-mods/curios)** support - Spyglass can now be equipped in the belt Curios slot and accessed via key-bind (Thanks Kimchiloof).
-   Fabric:
    -   **[Trinkets ](https://www.curseforge.com/minecraft/mc-mods/trinkets)** support - Spyglass can now be equipped in the belt trinket slot and accessed via key-bind.

### Changed

-   Forge:
    -   Mod now has a soft dependency on Curios API (on both client and server).
        -   If Curios API is not installed, the mod will still work on the client as before.
    -   Some backend code to make future updates easier.
-   Fabric:
    -   Mod now has a soft dependency on Trinkets (on both client and server).
        -   If Trinkets API is not installed, the mod will still work on the client as before.

### Fixed

-   Forge:
    -   Fix a bug where the player's active inventory/hot-bar slot de-syncs after opening the inventory while scoping.
        -   Instead of requiring the player to use the spyglass normally again to reset the slot, the spyglass remains where it was last used.

## [1.4] - 2022-09-13

Available versions:

-   Fabric, Quilt: 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4
-   Forge: 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4
-   NeoForge: 1.20.2, 1.20.3, 1.20.4

### Added

-   Fabric, Quilt:
    -   Can now use the spyglass and zoom while in third person.
    -   (2022-12-08) Updated to 1.19.3
    -   (2023-03-19) Updated to 1.19.4
    -   (2023-06-07) Updated to 1.20 and 1.20.1
    -   (2023-10-20) Updated to 1.20.2, 1.20.3, and 1.20.4, added Korean and Brazilian Portuguese translations (Thanks Yusi0 and FITFC).
-   Forge:
    -   Can now use the spyglass and zoom while in third person.
    -   Updated to 1.19.3 and 1.19.4
    -   (2023-06-18) Updated to 1.20 and 1.20.1
    -   (2023-10-20) Updated to 1.20.2, 1.20.3, and 1.20.4, added Korean translation (Thanks Yusi0).
-   NeoForge:
    -   Can now use the spyglass and zoom while in third person.
    -   (2023-10-20) Updated to 1.20.2, 1.20.3, and 1.20.4, added Korean translation (Thanks Yusi0).

### Fixed

-   Fabric, Quilt:
    -   Fix key-binds not working with renamed spyglass.
-   Forge, NeoForge:
    -   Fix key-binds not working with renamed spyglass.
    -   Fix key-binds not showing in the controls settings.

## [1.3.1] - 2022-07-28

Available versions:

-   Forge: 1.19, 1.19.1, 1.19.2

### Added

-   Forge:
    -   Updated to 1.19, 1.19.1, and 1.19.2

### Fixed

-   Forge:
    -   Fixed incompatibility with newer Forge versions

## [1.3] - 2022-06-08

Available versions:

-   Fabric: 1.19, 1.19.1, 1.19.2
-   Forge: 1.19

### Added

-   Fabric:
    -   Updated to 1.19, 1.19.1, and 1.19.2
-   Forge:
    -   Updated to 1.19

### Fixed

-   Fabric, Forge:
    -   Play sound when interacting with the spyglass while on creative mode.

## [1.2] - 2022-04-24

Available versions:

-   Fabric: 1.18.2
-   Forge: 1.18.1, 1.18.2

### Added

-   Fabric, Forge:
    -   Added option to hide the cross-hair while scoping.
    -   Added option to enable smooth (cinematic) camera while scoping.

## [1.1.1] - 2022-02-19

Available versions:

-   Forge: 1.18.1, 1.18.2

### Fixed

-   Forge:
    -   Fix logical Server and Client side bugs.

## [1.1] - 2022-02-11

Available versions:

-   Fabric: 1.18.1, 1.18.2
-   Forge: 1.18.1

### Added

-   Fabric:
    -   Re-release including fabric tag on jar to differentiate.
    -   (2022-02-28) Updated to 1.18.2
-   Forge:
    -   Initial Forge Release

## [1.0] - 2022-01-22

Available versions:

-   Fabric: 1.18.1

### Added

-   Fabric:
    -   Initial Release
