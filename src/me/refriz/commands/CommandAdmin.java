package me.refriz.commands;

import me.refriz.infractions.Ban;
import me.refriz.midstforth.quests.Quest;
import me.refriz.ranks.Rank;
import me.refriz.server.Messages;
import me.refriz.midstforth.engines.EngineMenu;
import me.refriz.server.SQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAdmin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

            if (player.isOp()) {

                int length = args.length;

                if (length == 1) {

                    if (args[0].equalsIgnoreCase("getrank")) {

                        player.sendMessage(ChatColor.GREEN + "Current rank: " + Rank.getRankTag(player));
                    }

                    if (args[0].equalsIgnoreCase("refresh")) {
                        Rank.refresh(player);
                        player.sendMessage(ChatColor.GREEN + "Refreshed");
                    }

                    if (args[0].equalsIgnoreCase("ban")) {
                        Ban.init(player, player.getName(), "Hacks");
                    }

                    if (args[0].equalsIgnoreCase("ship")) {
                        new EngineMenu().deploy(player);
                    }
                    if(args[0].equalsIgnoreCase("quest")){
                        Quest.addQuest(player, "test");
                    }
                    if(args[0].equalsIgnoreCase("assignquest")){
                        Quest.assignQuest(player, "test");
                    }

                    if(args[0].equalsIgnoreCase("finishquest")){
                        Quest.completeQuest(player, "test", "John", 0);
                    }
                }
                if(length == 2){
                    if(args[0].equalsIgnoreCase("nukemid")){
                        Player target = Bukkit.getPlayer(args[1]);

                        SQLHandler.action("DELETE FROM `locations` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `midstforth_economy` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `played_midstforth` WHERE `uuid` = '" + target.getUniqueId() + "'");

                        player.sendMessage(ChatColor.GREEN + "Operation successful");
                        target.kickPlayer(ChatColor.GREEN + "Data reset");
                    }
                    if(args[0].equalsIgnoreCase("nuke")){
                        Player target = Bukkit.getPlayer(args[1]);

                        SQLHandler.action("DELETE FROM `ranks` WHERE `uuid` = '" + target.getUniqueId() + "'");

                        player.sendMessage(ChatColor.GREEN + "Operation successful");
                        target.kickPlayer(ChatColor.GREEN + "Data reset");
                    }
                }
            } else {
                player.sendMessage(Messages.NO_PERM.getMessage());
            }
        return true;
    }
}
