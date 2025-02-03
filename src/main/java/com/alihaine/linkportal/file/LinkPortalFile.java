package com.alihaine.linkportal.file;

import com.alihaine.linkportal.LinkPortal;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkPortalFile {

    private final File portalLinkFile;
    private final FileConfiguration portalLinkConfig;
    private final LinkPortal linkPortalInstance = LinkPortal.getLinkPortal();
    private final Map<World, World> netherWorldsDestination = new HashMap<>();
    private final Map<World, World> endWorldsDestination = new HashMap<>();
    private World defaultNether;
    private World defaultEnd;
    private World defaultWorld;

    public LinkPortalFile() {
        this.portalLinkFile = linkPortalInstance.createCustomFile("linkportal.yml", getClass().getClassLoader().getResourceAsStream("linkportal.yml"));

        this.portalLinkConfig = new YamlConfiguration();
        try {
            portalLinkConfig.load(portalLinkFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        setValuesFromConfig();
    }

    private void setValuesFromConfig() {
        this.defaultNether = Bukkit.getWorld(this.portalLinkConfig.getString("default_nether"));
        this.defaultEnd = Bukkit.getWorld(this.portalLinkConfig.getString("default_end"));
        this.defaultWorld = Bukkit.getWorld(this.portalLinkConfig.getString("default_world"));

        fillMapWithWorldsValues(this.netherWorldsDestination, this.portalLinkConfig.getList("portal_nether_links"));
        fillMapWithWorldsValues(this.endWorldsDestination, this.portalLinkConfig.getList("portal_end_links"));
    }

    private void fillMapWithWorldsValues(Map<World, World> targetMap, List<?> lines) {
        if (lines == null || lines.isEmpty())
            return;

        lines.forEach((line) -> {
            String[] lineSplit = line.toString().split(":");
            if (lineSplit.length < 2) {
                Bukkit.getConsoleSender().sendMessage("Error for the line : " + line + " use the syntax WorldFrom:WorldDest");
                return;
            }
            World worldFrom = Bukkit.getWorld(lineSplit[0]);
            if (worldFrom == null) {
                Bukkit.getConsoleSender().sendMessage("Error the world: " + lineSplit[0] + " don't exist");
                return;
            }
            World worldDest = Bukkit.getWorld(lineSplit[1]);
            if (worldDest == null) {
                Bukkit.getConsoleSender().sendMessage("Error the world: " + lineSplit[0] + " don't exist");
                return;
            }
            targetMap.put(worldFrom, worldDest);
            Bukkit.getConsoleSender().sendMessage("New link created " + worldFrom.getName() + " to dest " + worldDest.getName());
        });
    }

    public World getWorldDestination(PlayerTeleportEvent.TeleportCause teleportCause, World fromWorld) {
        World worldDes;
        if (teleportCause.equals(PlayerTeleportEvent.TeleportCause.END_PORTAL))
            worldDes = endWorldsDestination.get(fromWorld);
        else
            worldDes = netherWorldsDestination.get(fromWorld);

        if (worldDes == null) {
            //If the player use a nether or end portal from a nether or end world, return the default world.
            if (fromWorld.getEnvironment().equals(World.Environment.NETHER) || fromWorld.getEnvironment().equals(World.Environment.THE_END))
                return defaultWorld;
            else if (teleportCause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL))
                return defaultNether;
            else
                return defaultEnd;
        }
        return worldDes;
    }
}
