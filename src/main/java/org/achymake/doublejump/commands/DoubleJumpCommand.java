package org.achymake.doublejump.commands;

import org.achymake.doublejump.DoubleJump;
import org.achymake.doublejump.data.Message;
import org.achymake.doublejump.data.Userdata;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DoubleJumpCommand implements CommandExecutor, TabCompleter {
    private DoubleJump getInstance() {
        return DoubleJump.getInstance();
    }
    private Userdata getUserdata(Player player) {
        return getInstance().getUserdata(player);
    }
    private Message getMessage() {
        return getInstance().getMessage();
    }
    public DoubleJumpCommand() {
        getInstance().getCommand("doublejump").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                getUserdata(player).toggleDoubleJump();
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("doublejump.command.doublejump.reload")) {
                        getInstance().reload();
                        getMessage().send(player, "&6DoubleJump:&f reloaded");
                        return true;
                    }
                }
            }
        } else if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 0) {
                getMessage().send(consoleCommandSender, getInstance().name() + " " + getInstance().version());
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    getInstance().reload();
                    getMessage().send(consoleCommandSender, "DoubleJump: reloaded");
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        var commands = new ArrayList<String>();
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (player.hasPermission("doublejump.command.doublejump.reload")) {
                    commands.add("reload");
                }
            }
        }
        return commands;
    }
}