package com.alihaine.linkportal.listener;

import com.alihaine.linkportal.LinkPortal;
import com.alihaine.linkportal.file.LinkPortalFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.TravelAgent;

public class PortalEvent implements Listener {
    private final LinkPortalFile linkPortalFile = LinkPortal.getLinkPortal().getLinkPortalFile();

    @EventHandler
    public void onPortalEvent(PlayerPortalEvent event) {
        World worldDestination = linkPortalFile.getWorldDestination(event.getCause(), event.getFrom().getWorld());
        TravelAgent travelAgent = event.getPortalTravelAgent();
        Location tmpDest = generateLocationDestination(event.getFrom().clone(), worldDestination);
        Location destination = travelAgent.findOrCreate(tmpDest);
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
        return environment.equals(World.Environment.NETHER) ? 8 : 0.125;
    }
}
