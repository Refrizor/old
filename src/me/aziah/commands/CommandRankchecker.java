package me.aziah.commands;

import me.aziah.ranks.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRankchecker implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("test1")) {
        }else{
            player.sendMessage("No permission.");
        }
        return true;
    }
}