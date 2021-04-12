package me.refriz.commands;

import me.refriz.midstforth.Regions;
import me.refriz.midstforth.quests.Intro;
import me.refriz.server.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMid implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if(PlayerData.getStaffBranchID(player) >=3){
            int length = args.length;

            if(length == 1){
                if(args[0].equalsIgnoreCase("leaveap")){
                    Regions.exitApartment(player, true);
                }
                if(args[0].equalsIgnoreCase("enterap")){
                    Regions.enterApartment(player, true);
                }
                if(args[0].equalsIgnoreCase("intro")){
                    Intro.init(player);
                }
                if(args[0].equalsIgnoreCase("stopintro")){
                    Regions.exitApartment(player, true);
                }
            }
        }
        return true;
    }
}
