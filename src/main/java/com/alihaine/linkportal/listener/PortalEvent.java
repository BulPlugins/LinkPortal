package com.alihaine.linkportal.listener;

import com.alihaine.bulmultiverse.BulMultiverse;
import com.alihaine.bulmultiverse.world.WorldDataManager;
import com.alihaine.linkportal.LinkPortal;
import com.alihaine.linkportal.file.LinkPortalFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalEvent implements Listener {
    private final WorldDataManager worldData = BulMultiverse.getWorldDataManager();
    private final LinkPortalFile linkPortalFile = LinkPortal.getLinkPortal().getLinkPortalFile();


    @EventHandler
    public void onPortalEvent(PlayerPortalEvent event) {
        event.useTravelAgent(true);
        Bukkit.getConsoleSender().sendMessage(event.getFrom().getWorld().getEnvironment().name());
        Bukkit.getConsoleSender().sendMessage("enter");
        if (event.getTo() == null)
            Bukkit.getConsoleSender().sendMessage("nu;l;l");
        World worldDest = linkPortalFile.getWorldDestination(World.Environment.NETHER, event.getFrom().getWorld());
        Location newLoc = new Location(worldDest, 0, 0, 0);
        //event.setTo(newLoc);
        //add "destrination", event.getWorld.destination
    }
}
