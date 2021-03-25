package me.refriz.commands;

import me.refriz.server.PlayerData;
import me.refriz.zeus.Warning;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWarn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if(PlayerData.getStaffBranchID().get(player.getName()) >=2){
            int length = args.length;

            if(length < 2){

            }
            if(length >=2){
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                String message = "";
                for(int i = 1; i < args.length; i++){
                    message = message + args[i] + " ";
                }

                Warning.add(player, message, target);
                player.sendMessage(ChatColor.AQUA + "Warning given to " + target.getName());

                if(target.isOnline()){
                    Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
                    onlineTarget.sendMessage(ChatColor.YELLOW + "You have received a warning. Details: " + ChatColor.RED + message);
                    onlineTarget.sendMessage(ChatColor.RED + "Please do not violate the rules or further action will be taken.");
                }
            }
        }
        return true;
    }
}
