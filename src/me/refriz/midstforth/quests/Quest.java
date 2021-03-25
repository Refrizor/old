package me.refriz.midstforth.quests;

import me.refriz.midstforth.Economy;
import me.refriz.server.DatabaseConnector;
import me.refriz.server.Messages;
import me.refriz.server.SQLHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Quest {

    public static void addQuest(Player player, String questName) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                SQLHandler.action("INSERT INTO `quests`(`uuid`, `name`) VALUES ('" + player.getUniqueId() + "', '" + questName + "')");
                player.sendMessage(Messages.QUEST_NEW.getMessage() + Messages.QUEST_NAME.getMessage() + questName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void assignQuest(Player player, String questName) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");

            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            if (resultSet.next()) {
                if (!resultSet1.next()) {
                    SQLHandler.action("INSERT INTO `active_quests`(`uuid`, `name`) VALUES ('" + player.getUniqueId() + "', '" + questName + "')");
                    player.sendMessage(Messages.QUEST_ASSIGN.getMessage() + Messages.QUEST_NAME.getMessage() + questName);
                } else {
                    player.sendMessage(ChatColor.RED + "Error: Already have quest assigned!");
                }
            } else {
                player.sendMessage(ChatColor.RED + "That quest does not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void completeQuest(Player player, String questName, String npc, int reward) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                SQLHandler.action("DELETE FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
                SQLHandler.action("DELETE FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
                SQLHandler.action("INSERT INTO `finished_quests`(`uuid`, `name`) VALUES ('" + player.getUniqueId() + "', '" + questName + "')");
                player.sendMessage(Messages.SPECIAL.getMessage() + Messages.QUEST_FINISH.getMessage() + Messages.QUEST_NAME.getMessage() + questName);

                Economy.add(player, reward);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasQuest(Player player, Object quest) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quests WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean hasFinishedQuest(Player player, Object quest) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM finished_quests WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getQuestName(Player player, Object quest) {
        String questName = null;
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM active_quests WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                questName = resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questName;
    }
}