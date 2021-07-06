package me.aziah.commands;

import me.aziah.server.Messages;
import me.aziah.server.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        try {

            if(PlayerData.getStaffBranchID(player) >=2) {

                if (player.getAllowFlight()) {
                    player.setAllowFlight(false);
                    player.sendMessage(ChatColor.AQUA + "Flight disabled");
                } else {
                    player.setAllowFlight(true);
                    player.sendMessage(ChatColor.AQUA + "Flight enabled");
                }
            }else{
                player.sendMessage(Messages.NO_PERM.getMessage());
            }
        }catch(Exception e){
            player.sendMessage(Messages.RANK_ERROR.getMessage() + e.getMessage());
        }
        return true;
    }
}
