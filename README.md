# Mod Updater

A user-friendly mod for adding, updating, and removing mods in-game, and even auto updating. Ideal for servers, but works
on clients too.


# Commands
All commands are accessed via `/modupdater <command> <args>`

### Download Command
Version argument is optional, the latest version is downloaded if not specified
- `download (modrinth|curseforge) <slug> [<version>]`
    - *`download modrinth disable-portals 0.1.0`*
    <br><br>
- `download github <owner> <repo> [<version>]`
    - *`download github SpaceClouds42 FabricZones`*
    <br><br>
- `download direct <link>`
    - *`download direct https://github.com/Archydra-Studios/Territorial-Base/releases/download/v0.0.2/territorial-0.0.2.jar`*

### Remove Command
Use the mod's id to remove a mod. When using the command, it will suggest all the currently installed mods, so you can
use tab completion if you don't know the mod id
- `remove <mod-id>`

### Update Command
To update a mod with a direct link, you can simply use `/remove` and `/download`
- `update (modrinth|curseforge|github) <slug> [<version>]`
    - *`update curseforge gift-it`*
  
### List Command
List all active mods and brief information about them
