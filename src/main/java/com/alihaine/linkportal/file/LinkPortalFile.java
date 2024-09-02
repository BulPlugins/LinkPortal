package com.alihaine.linkportal.file;

import com.alihaine.linkportal.LinkPortal;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LinkPortalFile {

    private final File portalLinkFile;
    private final FileConfiguration portalLinkConfig;
    private final LinkPortal linkPortalInstance = LinkPortal.getLinkPortal();
    private final Map<World, World> worldsDestinations = new HashMap<>();
    private World defaultNether;
    private World defaultEnd;

    public LinkPortalFile() {
        try {
            this.portalLinkFile = linkPortalInstance.createCustomFile("linkportal.yml", getClass().getClassLoader().getResourceAsStream("linkportal.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

        this.portalLinkConfig.getList("portal_nether_links").forEach((line) -> {
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
            this.worldsDestinations.put(worldFrom, worldDest);
            Bukkit.getConsoleSender().sendMessage("New link created " + worldFrom.getName() + " to dest " + worldDest.getName());
        });
    }

    public World getWorldDestination(World.Environment environment, World fromWorld) {
        World worldDes = worldsDestinations.get(fromWorld);
        if (worldDes == null) {
            if (environment == World.Environment.NETHER)
                return defaultNether;
            else
                return defaultEnd;
        }
        return worldDes;
    }
}
