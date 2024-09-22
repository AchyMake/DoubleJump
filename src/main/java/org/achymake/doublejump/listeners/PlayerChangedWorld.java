package org.achymake.doublejump.listeners;

import org.achymake.doublejump.DoubleJump;
import org.achymake.doublejump.data.Userdata;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerChangedWorld implements Listener {
    private DoubleJump getInstance() {
        return DoubleJump.getInstance();
    }
    private FileConfiguration getConfig() {
        return getInstance().getConfig();
    }
    private Userdata getUserdata(Player player) {
        return getInstance().getUserdata(player);
    }
    private PluginManager getManager() {
        return getInstance().getManager();
    }
    public PlayerChangedWorld() {
        getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        var player = event.getPlayer();
        var world = player.getWorld();
        var worldName = world.getName();
        if (getConfig().getBoolean("force-double-jump." + worldName)) {
            getUserdata(player).toggleDoubleJump();
        } else if (getUserdata(player).hasDoubleJump()) {
            getUserdata(player).toggleDoubleJump();
        }
    }
}