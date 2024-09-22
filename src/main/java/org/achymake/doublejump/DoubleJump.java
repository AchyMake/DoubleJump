package org.achymake.doublejump;

import org.achymake.doublejump.commands.DoubleJumpCommand;
import org.achymake.doublejump.data.Userdata;
import org.achymake.doublejump.data.Message;
import org.achymake.doublejump.handlers.ScheduleHandler;
import org.achymake.doublejump.listeners.PlayerChangedWorld;
import org.achymake.doublejump.listeners.PlayerJoin;
import org.achymake.doublejump.listeners.PlayerMove;
import org.achymake.doublejump.listeners.PlayerToggleFlight;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class DoubleJump extends JavaPlugin {
    private static DoubleJump instance;
    private Message message;
    private ScheduleHandler scheduleHandler;
    private UpdateChecker updateChecker;
    private PluginManager manager;
    @Override
    public void onEnable() {
        instance = this;
        message = new Message();
        scheduleHandler = new ScheduleHandler();
        updateChecker = new UpdateChecker();
        manager = getServer().getPluginManager();
        commands();
        events();
        reload();
        sendInfo("Enabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
        getUpdateChecker().getUpdate();
    }
    @Override
    public void onDisable() {
        getScheduleHandler().cancelAll();
    }
    private void commands() {
        new DoubleJumpCommand();
    }
    private void events() {
        new PlayerChangedWorld();
        new PlayerJoin();
        new PlayerMove();
        new PlayerToggleFlight();
    }
    public void reload() {
        var file = new File(getDataFolder(), "config.yml");
        if (file.exists()) {
            try {
                getConfig().load(file);
            } catch (IOException | InvalidConfigurationException e) {
                sendWarning(e.getMessage());
            }
        } else {
            getConfig().options().copyDefaults(true);
            try {
                getConfig().save(file);
            } catch (IOException e) {
                sendWarning(e.getMessage());
            }
        }
    }
    public PluginManager getManager() {
        return manager;
    }
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
    public ScheduleHandler getScheduleHandler() {
        return scheduleHandler;
    }
    public Message getMessage() {
        return message;
    }
    public Userdata getUserdata(Player player) {
        return new Userdata(player);
    }
    public static DoubleJump getInstance() {
        return instance;
    }
    public void sendInfo(String message) {
        getLogger().info(message);
    }
    public void sendWarning(String message) {
        getLogger().warning(message);
    }
    public String name() {
        return getDescription().getName();
    }
    public String version() {
        return getDescription().getVersion();
    }
    public String getMinecraftVersion() {
        return getServer().getBukkitVersion();
    }
    public String getMinecraftProvider() {
        return getServer().getName();
    }
    public boolean isSpigot() {
        return getMinecraftProvider().equals("CraftBukkit");
    }
}