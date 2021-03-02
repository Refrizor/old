package me.refriz.midstforth.quests;

import me.refriz.server.DatabaseConnector;
import me.refriz.server.SQLHandler;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Quest {

    public static void addQuest(Player player, String questName){
        try{
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                SQLHandler.action("INSERT INTO `quests`(`uuid`, `name`) VALUES ('" + player.getUniqueId() + "', '" + questName + "')");
                player.sendMessage("Received quest!");
            }else{
                player.sendMessage("Error: Already have quest!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void assignQuest(Player player, String questName){
        try{
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                SQLHandler.action("INSERT INTO `active_quests`(`uuid`, `name`) VALUES ('" + player.getUniqueId() + "', '" + questName + "')");
                player.sendMessage("Quest assigned!");
            }else{
                player.sendMessage("Error: Already have quest assigned!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void completeQuest(Player player, String questName, String npc, int reward){
        try{
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                SQLHandler.action("DELETE FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
                SQLHandler.action("DELETE FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
                SQLHandler.action("INSERT INTO `finished_quests`(`uuid`, `name`) VALUES ('" + player.getUniqueId() + "', '" + questName + "')");
                player.sendMessage("Quest completed!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}