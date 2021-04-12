package me.refriz.minigames.pvp;

import org.bukkit.Bukkit;
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
            if(args[0].equalsIgnoreCase("join")) {
                if (!PVPGame.inQueue(player)) {
                    PVPGame.getQueue().add(player.getName());
                    new PVPGame().startPrematchTimer(player);

                    for(Player all : Bukkit.getServer().getOnlinePlayers()){
                        if(PVPGame.inQueue(all)){
                            all.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + " joined queue");
                        }
                    }
                }
            }
        }
        return true;
    }
}
