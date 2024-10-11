package com.alihaine.linkportal;

import com.alihaine.bulmultiverse.BulMultiverse;
import com.alihaine.bulmultiverse.BulMultiverseAddon;
import com.alihaine.linkportal.file.LinkPortalFile;
import com.alihaine.linkportal.listener.PortalEvent;
import org.bukkit.Bukkit;

public class LinkPortal extends BulMultiverseAddon {

    private static LinkPortal linkPortal;
    private LinkPortalFile linkPortalFile;

    @Override
    public void onEnable() {
        linkPortal = this;

        Bukkit.getConsoleSender().sendMessage("§e[BulMultiverse] §aEnable the addon LinkPortal");
    }

    @Override
    public void onEnableAfterWorldsLoad() {
        linkPortalFile = new LinkPortalFile();

        BulMultiverse.getBulMultiverseInstance().getServer().getPluginManager().registerEvents(new PortalEvent(), BulMultiverse.getBulMultiverseInstance());
    }

    @Override
    public void onDisable() {

    }

    public static LinkPortal getLinkPortal() {
        return linkPortal;
    }

    public LinkPortalFile getLinkPortalFile() { return linkPortalFile; }
}
