package me.aziah.zeus;

import me.aziah.Inferris;
import me.aziah.server.SQLHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Kick {

    public static void init(Player punisher, String reason, Player target, boolean args, boolean log, boolean message){

        if(args) {
            target.kickPlayer(ChatColor.RED + "You have been kicked!\nReason: " + ChatColor.YELLOW + reason);
            if(log){
                reason = ChatColor.stripColor(reason);
                SQLHandler.action("INSERT INTO `infractions`(`uuid`, `type`, `reason`, `moderator`) VALUES ('" + target.getUniqueId() + "', 'Kick', '" + reason + "', '" + punisher.getUniqueId() + "')");
            }
        }else{
            target.kickPlayer(ChatColor.RED + "You have been kicked!");
        }
        if(message){
            Inferris.sendGlobalMessage(ChatColor.RED + target.getName() + " has been kicked");
        }
    }
}
