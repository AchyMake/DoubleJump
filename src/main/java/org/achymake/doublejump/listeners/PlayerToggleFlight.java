package org.achymake.doublejump.listeners;

import org.achymake.doublejump.DoubleJump;
import org.achymake.doublejump.data.Userdata;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

public class PlayerToggleFlight implements Listener {
    private DoubleJump getInstance() {
        return DoubleJump.getInstance();
    }
    private Userdata getUserdata(Player player) {
        return getInstance().getUserdata(player);
    }
    private PluginManager getManager() {
        return getInstance().getManager();
    }
    public PlayerToggleFlight() {
        getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        var player = event.getPlayer();
        var userdata = getUserdata(player);
        if (!userdata.hasDoubleJump())return;
        event.setCancelled(true);
        if (!event.isCancelled())return;
        var jumped = userdata.getJumped();
        if (jumped == 0) {
            var vector = player.getVelocity();
            var direction = player.getLocation().getDirection();
            var x = direction.getX();
            var y = 0.40;
            var z = direction.getZ();
            if (player.isSneaking()) {
                player.setVelocity(new Vector(vector.getX(), 0.55, vector.getZ()));
            } else if (player.isSprinting()) {
                player.setVelocity(new Vector(x/2, y, z/2));
            } else {
                player.setVelocity(new Vector(x/4, y, z/4));
            }
            userdata.addJump(1);
            player.setAllowFlight(false);
            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 5, 0.2, 0.1, 0.2, 0);
            getInstance().getServer().getOnlinePlayers().forEach(players -> players.playSound(player, Sound.BLOCK_BIG_DRIPLEAF_STEP, 0.5F, 0.7F));
        }
    }
}