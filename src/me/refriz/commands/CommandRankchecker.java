package me.refriz.commands;

import me.refriz.ranks.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRankchecker implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission("test1")) {
            player.sendMessage(Rank.ADMIN.getRank().size() + "");
            player.sendMessage(Rank.MOD.getRank().size() + "");
            player.sendMessage(Rank.HELPER.getRank().size() + "");
            player.sendMessage("");
            player.sendMessage(Rank.DONOR.getRank().size() + "");
            player.sendMessage("");
            player.sendMessage(Rank.NONE.getRank().size() + "");
        }else{
            player.sendMessage("No permission.");
        }
        return true;
    }
}