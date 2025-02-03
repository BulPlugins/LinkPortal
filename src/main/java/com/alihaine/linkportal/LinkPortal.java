package com.alihaine.linkportal;

import com.alihaine.bulmultiverse.BulMultiverse;
import com.alihaine.bulmultiverse.addon.BulMultiverseAddon;
import com.alihaine.linkportal.file.LinkPortalFile;
import com.alihaine.linkportal.listener.PortalEvent;
import org.bukkit.Bukkit;

import java.util.Collections;

public class LinkPortal extends BulMultiverseAddon {

    private static LinkPortal linkPortal;
    private LinkPortalFile linkPortalFile;

    public LinkPortal() {
        super("LinkPortal", Collections.singletonList("AliHaine"), Collections.singletonList("https://www.spigotmc.org/resources/beta-addon-linkportal.119396/"), "https://discord.com/invite/wxnTV68dX2");
    }

    @Override
    public void onEnable() {
        linkPortal = this;

        Bukkit.getConsoleSender().sendMessage("§e[BulMultiverse] §aEnable the addon LinkPortal");
    }

    @Override
    public void onEnableAfterWorldsLoad() {
        linkPortalFile = new LinkPortalFile();

        BulMultiverse.getBulMultiverse().getServer().getPluginManager().registerEvents(new PortalEvent(), BulMultiverse.getBulMultiverse());
    }

    @Override
    public void onDisable() {

    }

    public static LinkPortal getLinkPortal() {
        return linkPortal;
    }

    public LinkPortalFile getLinkPortalFile() { return linkPortalFile; }
}
