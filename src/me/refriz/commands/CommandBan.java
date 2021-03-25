package me.refriz.commands;

import me.refriz.Inferris;
import me.refriz.server.PlayerData;
import me.refriz.zeus.Ban;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBan implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if(PlayerData.getStaffBranchID(player) >=2) {
            int length = args.length;

            if (length >= 2) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (!Ban.isBanned(target)) {

                    String message = "";
                    for (int i = 1; i < args.length; i++) {
                        message = message + args[i] + " ";
                    }

                    Ban.add(player, message, target);

                    Inferris.sendGlobalMessage(ChatColor.AQUA + target.getName() + " has been banned");
                    player.sendMessage(ChatColor.GREEN + target.getName() + " banned");
                }else{
                    player.sendMessage(ChatColor.RED + "Player is already banned!");
                }
            }
        }
        return true;
    }
}
