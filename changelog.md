# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/) and this project adheres to [Semantic Versioning](http://semver.org/).

Find your version by looking for the file for your mod-loader:

-   spyglass_improvements-`<mod-version>`+mc`<mc-version>`+forge.jar
-   spyglass-improvements-`<mod-version>`+mc`<mc-version>`+fabric.jar

## [2.0.0] - 2024-mm-dd

Available versions:

-   Forge: 1.18.2, 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4, 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4

### Added

-   Forge:
    -   **[Curios API](https://www.curseforge.com/minecraft/mc-mods/curios)** support - Spyglass can now be equipped in the belt Curios slot and accessed via key-bind (Thanks Kimchiloof).

### Changed

-   Forge:
    -   Mod now has a soft dependency on Curios API (on both client and server).
        -   If Curios API is not installed, the mod will still work on the client as before.
    -   Some backend code to make future updates easier.

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
