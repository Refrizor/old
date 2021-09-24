package me.aziah.commands;

import me.aziah.events.Cinematics;
import me.aziah.midstforth.Regions;
import me.aziah.midstforth.quests.list.QuestIntro;
import me.aziah.server.PlayerData;
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
                    QuestIntro.deploy(player);
                }
                if(args[0].equalsIgnoreCase("stopintro")){
                    Regions.exitApartment(player, true);
                }
                if(args[0].equalsIgnoreCase("cinematic")){
                    new Cinematics().introduction(player);
                }
            }
        }
        return true;
    }
}
