package me.aziah.commands;

import me.aziah.Inferris;
import me.aziah.server.PlayerStates;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVanish implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        if (length == 1) {
            if (args[0].equalsIgnoreCase("on")) {
                if (!PlayerStates.getVanish().contains(player.getName())) {
                    PlayerStates.getVanish().add(player.getName());
                }
                player.sendMessage(ChatColor.GREEN + "You are now vanished");

                for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                    if (!all.isOp()) {
                        all.hidePlayer(Inferris.getInstance(), player);
                    }
                }
            }

            if (args[0].equalsIgnoreCase("off")) {
                if (PlayerStates.getVanish().contains(player.getName())) {
                    PlayerStates.getVanish().remove(player.getName());
                }
                player.sendMessage(ChatColor.GREEN + "You are no longer vanished");

                for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                    all.showPlayer(Inferris.getInstance(), player);
                }
            }
        }
        return true;
    }
}