# LinkPortal

This is a addon for the plugin [BulMultiverse](https://github.com/BulPlugins/BulMultiverse). LinkPortal allow you to link nether or end portal to specific world. [Download page](https://www.spigotmc.org/resources/119396/ "Click to download")

## Configuration file
World names are case-sensitive, so if your world name is "World", you must set it in the config as "World", not "world".
```
//If a nether portal is used in a world where a destination is not specified, will teleport to this default world
default_nether: world_nether
//If a end portal is used in a world where a destination is not specified, will teleport to this default world
default_end: world_the_end
//If a end or nether portal is used in a end or nether world where a destination is not specified, will teleport to this default world
default_world: world

//Link all your nether portals
portal_nether_links:
// The left (before the ':') is the world from and the right is the world destination)
#  - YourSuperWorldName:TheWorldDestination

//Link all your end portal 
portal_end_links:
#  - YourSuperWorldName:TheWorldDestination
```

## No restriction
There are no restrictions or controls, when a player use a nether portal, the destination does not necessarily need to be a nether world, and same for end:
```
portal_nether_links:
   - MyWorld:world_the_end
```
 In this exemple, if you use a nether portal in the world "MyWorld", you will be teleported to the world "world_the_end".

## Distribution

This is a public plugin. You are free to use it and create a fork to develop your own version. However you are not allowed to sell or distribute it in a private manner.