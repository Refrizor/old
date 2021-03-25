package me.refriz.commands;

import me.refriz.server.PlayerData;
import me.refriz.zeus.Ban;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if(PlayerData.getStaffBranchID().get(player.getName()) >=2){
            int length = args.length;

            if(length == 0){

            }
            if(length == 1){
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                if(Ban.isBanned(target)){
                    Ban.remove(target);
                    player.sendMessage(ChatColor.GREEN + target.getName() + " unbanned");
                }else{
                    player.sendMessage(ChatColor.RED + "Player is not banned!");
                }
            }
        }
        return true;
    }
}
