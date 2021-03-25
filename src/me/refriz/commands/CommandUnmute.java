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

import java.util.UUID;

public class CommandUnmute implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if(PlayerData.getStaffBranchID().get(player.getName()) >=2) {
            int length = args.length;

            if(length == 0){
                player.sendMessage(ChatColor.RED + "/unmute <player>");
            }

            if (length == 1) {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (PlayerData.isMuted(offlinePlayer)) {
                    Mute.unmute(offlinePlayer);
                    player.sendMessage(ChatColor.AQUA + offlinePlayer.getName() + " has been unmuted");

                    if (Bukkit.getOfflinePlayer(args[0]).isOnline()) {
                        PlayerData.getMuted().remove(Bukkit.getPlayer(args[0]).getName());
                        Player onlinePlayer = Bukkit.getPlayer(offlinePlayer.getUniqueId());
                        onlinePlayer.sendMessage(ChatColor.YELLOW + "Your mute has been lifted");
                    }
                }else{
                    player.sendMessage(ChatColor.RED + "That player is not muted!");
                }
            }
        }
        return true;
    }
}
