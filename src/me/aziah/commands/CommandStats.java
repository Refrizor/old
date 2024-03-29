package me.aziah.commands;

import me.aziah.midstforth.Economy;
import me.aziah.midstforth.Midstforth;
import me.aziah.ranks.Rank;
import me.aziah.server.DatabaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandStats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        if (length == 0) {
            player.sendMessage(ChatColor.AQUA + "STATS");
            player.sendMessage("");
            player.sendMessage(ChatColor.GRAY + "Rank: " + Rank.getRankTag(player));
            player.sendMessage("");
            player.sendMessage(ChatColor.YELLOW + "Engine: " + ChatColor.AQUA + Midstforth.getEngine(player));
            if (Midstforth.hasPlayed(player)) {
                player.sendMessage(ChatColor.YELLOW + "Money: " + ChatColor.DARK_GREEN + "$" + Economy.retrieve(player));
                player.sendMessage("");

                int finishedQuests = 0;
                try {
                    Connection connection = DatabaseHandler.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM completed_quests WHERE `uuid` = '" + player.getUniqueId() + "'");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        finishedQuests++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    player.sendMessage(e.getMessage());
                }
                player.sendMessage(ChatColor.YELLOW + "Completed quests: " + ChatColor.AQUA + finishedQuests);
                player.sendMessage("");
                player.sendMessage(ChatColor.YELLOW + "Current location: " + ChatColor.GREEN + Midstforth.getLocation(player));
            }
        }
        return true;
    }
}
