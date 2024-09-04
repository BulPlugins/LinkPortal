package com.alihaine.linkportal.listener;

import com.alihaine.linkportal.LinkPortal;
import com.alihaine.linkportal.file.LinkPortalFile;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalEvent implements Listener {
    private final LinkPortalFile linkPortalFile = LinkPortal.getLinkPortal().getLinkPortalFile();

    @EventHandler
    public void onPortalEvent(PlayerPortalEvent event) {
        World worldDestination = linkPortalFile.getWorldDestination(event.getCause(), event.getFrom().getWorld());
        Location destination;
        Location tmpDest = generateLocationDestination(event.getFrom().clone(), worldDestination);
        try {
            Class.forName("org.bukkit.TravelAgent");
            TravelAgent travelAgent = event.getPortalTravelAgent();
            destination = travelAgent.findOrCreate(tmpDest);
        } catch (ClassNotFoundException exception) {
            Bukkit.getConsoleSender().sendMessage("TravelAgent not available");
            destination = tmpDest;
        }
        if (destination.getWorld().getEnvironment().equals(World.Environment.THE_END))
            checkEndPlatform(destination.clone());
        event.setTo(destination);
        event.getPlayer().sendMessage("You have been teleported to the world: " + worldDestination.getName());
    }

    private Location generateLocationDestination(Location fromLocationClone, World worldDestination) {
        if (worldDestination.getEnvironment().equals(World.Environment.THE_END)) {
            fromLocationClone.setX(100);
            fromLocationClone.setY(49);
            fromLocationClone.setZ(0);
        } else {
            double scaling = getScaling(worldDestination.getEnvironment());
            fromLocationClone.setX(fromLocationClone.getX() * scaling);
            fromLocationClone.setZ(fromLocationClone.getZ() * scaling);
        }
        fromLocationClone.setWorld(worldDestination);
        return fromLocationClone;
    }

    private double getScaling(World.Environment environment) {
        return environment.equals(World.Environment.NETHER) ? 0.125 : 8;
    }

    private void checkEndPlatform(Location cloneLocation) {
        cloneLocation.setY(cloneLocation.getY() - 1);

        if (!cloneLocation.getBlock().getType().equals(Material.OBSIDIAN)) {
            Bukkit.getConsoleSender().sendMessage("NO PLATFORM");
            generateEndPlatform(cloneLocation);
        }
    }

    private void generateEndPlatform(Location location) {
        location.getBlock().setType(Material.OBSIDIAN);
        int x = 97;
        while (x++ < 102) {
            int z = -4;
            while (z++ < 1) {
                Location loc = new Location(location.getWorld(), x, 48, z);
                loc.getBlock().setType(Material.OBSIDIAN);
            }
        }
    }
}
