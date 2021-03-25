package me.refriz.commands;

import me.refriz.Inferris;
import me.refriz.server.PlayerData;
import me.refriz.zeus.Mute;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMute implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;

        if(PlayerData.getStaffBranchID().get(player.getName()) >=2){
            int length = args.length;

            if(length < 2){

            }
            if(length == 2) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (!PlayerData.isMuted(target)) {

                    String message = "";
                    for (int i = 1; i < args.length; i++) {
                        message = message + args[i] + " ";
                    }

                    Mute.mute(player, message, target);
                    player.sendMessage(ChatColor.AQUA + target.getName() + " has been muted");
                    Inferris.sendGlobalMessage(ChatColor.RED + "" + target.getName() + " was muted [" + message + "]");

                    if (target.isOnline()) {
                        Player onlinePlayer = target.getPlayer();
                        onlinePlayer.sendMessage(ChatColor.YELLOW + "You have received a mute");
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "Player is already muted");
                }
            }
        }
        return true;
    }
}
