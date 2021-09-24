package me.aziah.minigames.pvp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPVP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        if(length == 1){
            if(args[0].equalsIgnoreCase("join")){
                if(!PVP.getQueue().contains(player.getUniqueId())){
                    PVP.getQueue().add(player.getUniqueId());

                    if(PVP.getQueue().size() >=2){
                        PVP.startCountdown();
                    }
                }else{
                    player.sendMessage(Messages.ERROR_ALREADY_IN_QUEUE.getMessage());
                }
            }

            if(args[0].equalsIgnoreCase("quit")){
                if(PVP.getQueue().contains(player.getUniqueId())){
                    PVP.getQueue().remove(player.getUniqueId());
                }else{
                    player.sendMessage(Messages.ERROR_NOT_IN_QUEUE.getMessage());
                }
            }

            if(args[0].equalsIgnoreCase("team")){
                if (Teams.getTeamOfPlayer(player) == Teams.Team.BLUE) {
                    player.sendMessage(Messages.BLUE_START.getMessage());
                }
                if (Teams.getTeamOfPlayer(player) == Teams.Team.RED) {
                    player.sendMessage(Messages.RED_START.getMessage());
                }
            }
        }
        return true;
    }
}
