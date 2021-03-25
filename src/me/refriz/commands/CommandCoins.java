package me.refriz.commands;

import me.refriz.Coins;
import me.refriz.server.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCoins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        int getBalance = 0;

        if(PlayerData.getStaffBranchID(player) >=3) {
            if (length == 0) {
                player.sendMessage(ChatColor.AQUA + "Coins: " + ChatColor.YELLOW +  Coins.retrieve(player));
            }
            if (length == 3) {
                //coins set Player #
                Player target = Bukkit.getPlayer(args[1]);
                int amount = Integer.parseInt(args[2]);

                if (args[0].equalsIgnoreCase("set")) {

                    Coins.set(player, amount);

                    player.sendMessage(ChatColor.GREEN + "Updated balance for " + target.getName());
                }

                if (args[0].equalsIgnoreCase("add")) {

                    Coins.add(player, amount);
                    player.sendMessage(ChatColor.GREEN + "Updated balance for " + target.getName());
                }

                if (args[0].equalsIgnoreCase("remove")) {

                    Coins.remove(player, amount);

                    player.sendMessage(ChatColor.GREEN + "Updated balance for " + target.getName());
                }
            }
        }
        return true;
    }
}