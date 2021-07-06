package me.aziah.commands;

import me.aziah.midstforth.quests.Quest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandQuestManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        if(length < 2){
            player.sendMessage("/questmanager <add,remove,finish> <quest_name>");
        }
        if(length == 2){
            if(args[0].equalsIgnoreCase("add")){
                Quest.addQuest(player, args[1], null, false);
            }
            if(args[0].equalsIgnoreCase("remove")){
                Quest.deleteQuest(player, args[1], true);
            }
            if(args[0].equalsIgnoreCase("finish")){
                Quest.completeQuest(player, args[1], null, 5);
            }
        }
        return true;
    }
}
