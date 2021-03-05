package me.refriz.commands;

import me.refriz.midstforth.quests.Quest;
import me.refriz.midstforth.quests.Quests;
import me.refriz.server.DatabaseConnector;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandQuest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        String questName = null;

        if (length == 0) {
            try {
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM active_quests WHERE `uuid` = '" + player.getUniqueId() + "'");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    questName = resultSet.getString(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(e.getMessage());
            }

            if (questName != null) {
                player.sendMessage(ChatColor.GREEN + "Current quest: " + ChatColor.WHITE + questName);
            } else {
                player.sendMessage(ChatColor.GREEN + "Current quest: " + ChatColor.RED + "No active quest");
            }
        }

        if (length == 1) {
            if (args[0].equalsIgnoreCase("completed")) {
                try {
                    Connection connection = DatabaseConnector.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM finished_quests WHERE `uuid` = '" + player.getUniqueId() + "'");
                    ResultSet resultSet = preparedStatement.executeQuery();

                    StringBuilder stringBuilder = new StringBuilder();

                    while (resultSet.next()) {
                        String quests = resultSet.getString(1);
                        stringBuilder.append(ChatColor.YELLOW).append(quests).append(ChatColor.WHITE).append(", ");
                    }
                    String quests = stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).toString();

                    player.sendMessage(ChatColor.GREEN + "Finished quests:\n" + quests);
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "You don't have any completed quests");
                }
            }
        }

        if (length == 2) {
            if (args[0].equalsIgnoreCase("start")) {
                String quest = args[1];
                if(quest.equalsIgnoreCase(Quests.TEST_1.getName())) {
                    Quest.assignQuest(player, Quests.TEST_1.getName());
                }else{
                    player.sendMessage(ChatColor.RED + "Quest not found");
                }
            }
        }
        return true;
    }
}
