package org.achymake.doublejump.listeners;

import org.achymake.doublejump.DoubleJump;
import org.achymake.doublejump.data.Userdata;
import org.achymake.doublejump.handlers.ScheduleHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerMove implements Listener {
    private DoubleJump getInstance() {
        return DoubleJump.getInstance();
    }
    private Userdata getUserdata(Player player) {
        return getInstance().getUserdata(player);
    }
    private ScheduleHandler getScheduler() {
        return getInstance().getScheduleHandler();
    }
    private PluginManager getManager() {
        return getInstance().getManager();
    }
    public PlayerMove() {
        getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event) {
        var from = event.getFrom();
        var to = event.getTo();
        if (!hasMoved(from, to))return;
        var player = event.getPlayer();
        var userdata = getUserdata(player);
        if (userdata.hasDoubleJump()) {
            if (userdata.getJumped() > 0) {
                if (!player.isOnGround())return;
                getScheduler().runLater(new Runnable() {
                    @Override
                    public void run() {
                        userdata.resetJump();
                        player.setAllowFlight(true);
                    }
                }, 0);
            }
        }
    }
    private boolean hasMoved(Location from, Location to) {
        if (to == null)return false;
        else return from.getX() != to.getX()
                || from.getY() != to.getY()
                || from.getZ() != to.getZ();
    }
}