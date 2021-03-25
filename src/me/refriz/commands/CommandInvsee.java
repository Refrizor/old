package me.refriz.commands;

import me.refriz.server.Messages;
import me.refriz.server.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandInvsee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;

        int length = args.length;

        try {

            if(PlayerData.getStaffBranchID(player) >=3) {
                if (length == 0 || length > 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
                }
                if (length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    Inventory inventory = target.getInventory();
                    player.openInventory(inventory);
                }
            }else{
                player.sendMessage(Messages.NO_PERM.getMessage());
            }
        } catch (Exception e) {
            player.sendMessage(Messages.RANK_ERROR.getMessage() + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}