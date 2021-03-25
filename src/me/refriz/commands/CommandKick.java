package me.refriz.commands;

import me.refriz.server.PlayerData;
import me.refriz.zeus.Kick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if(PlayerData.getStaffBranchID(player) >=2){
            int length = args.length;
            Player target = Bukkit.getPlayer(args[0]);

            if(length == 0){
                player.sendMessage(ChatColor.RED + "Usage: /kick <player> [reason]");
            }

            if(length == 1){
                Kick.init(player,null, target, false, true, true);
            }
            if(length >=2){
                String message = "";
                for(int i = 1; i < args.length; i++){
                    message = message + args[i] + " ";
                }

                Kick.init(player, ChatColor.YELLOW + message, target, true, true, true);
            }
        }
        return true;
    }
}
