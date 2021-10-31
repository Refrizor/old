package me.aziah.midstforth.quests;

import me.aziah.Inferris;
import me.aziah.midstforth.Economy;
import me.aziah.server.DatabaseHandler;
import me.aziah.server.Messages;
import me.aziah.server.PlayerUtils;
import me.aziah.server.SQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Quest {

    public static void addQuest(OfflinePlayer player, Object quest, String description, boolean forceAssign){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `quests`(`uuid`, `name`, `description`) VALUES ('" + player.getUniqueId() + "', '" + quest + "', '" + description + "')");
                preparedStatement1.execute();

                if(player.isOnline()){
                    player.getPlayer().sendMessage(Messages.QUEST_RECEIVE.getMessage() + quest);
                }
            }

            if(forceAssign){
                assignQuest(player, quest, true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void assignQuest(OfflinePlayer player, Object quest, boolean sendMessage){
        try{
            Connection connection = DatabaseHandler.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `description` FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String description = resultSet.getString(1);

                ResultSet resultSet1 = preparedStatement1.executeQuery();

                if (!resultSet1.next()) {
                    SQLHandler.action("DELETE FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "'");
                    PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO `active_quests`(`uuid`, `name`,`description`) VALUES ('" + player.getUniqueId() + "', '" + quest + "', '" + description + "')");
                    preparedStatement2.execute();

                    if(sendMessage) {

                        if (player.isOnline()) {
                            Player online = player.getPlayer();
                            online.sendMessage(ChatColor.YELLOW + "Quest assigned!");
                        }
                    }

                } else {
                    if (player.isOnline()) {
                        Player online = player.getPlayer();
                        online.sendMessage(ChatColor.RED + "You already have that quest assigned!");
                    }
                }
            }else{
                if(player.isOnline()){
                    Player online = player.getPlayer();
                    online.sendMessage(ChatColor.RED + "You don't have that quest unlocked.");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void unassignQuest(OfflinePlayer player, Object quest){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM active_quests WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                SQLHandler.action("DELETE FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "'");
                if(player.isOnline()){
                    Player online = player.getPlayer();
                    online.sendMessage(ChatColor.YELLOW + "Quest is no longer active.");
                }
            }else{
                if(player.isOnline()){
                    Player online = player.getPlayer();
                    online.sendMessage(ChatColor.RED + "Quest is not assigned.");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteQuest(OfflinePlayer player, Object quest, boolean wipeProgress){
        SQLHandler.action("DELETE FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
        SQLHandler.action("DELETE FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");

        if(wipeProgress){
            SQLHandler.action("DELETE FROM `completed_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
        }
    }

    public static void completeQuest(Player player, String questName, String npc, int reward) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                SQLHandler.action("DELETE FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
                SQLHandler.action("DELETE FROM `quests` WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + questName + "'");
                SQLHandler.action("INSERT INTO `completed_quests`(`uuid`, `name`) VALUES ('" + player.getUniqueId() + "', '" + questName + "')");
                player.sendMessage(Messages.SPECIAL.getMessage() + Messages.QUEST_FINISH.getMessage() + questName);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        player.sendMessage(ChatColor.YELLOW + "You received " + ChatColor.GREEN + "$" + reward + ChatColor.YELLOW + " in your bank for completion.");
                        Economy.add(player, reward);
                    }
                }, 60L);

                PlayerUtils.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1F);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasQuest(Player player, Object quest) {
        try {
            Connection connection = DatabaseHandler.getConnection();
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

    public static boolean hasActiveQuest(Player player, Object quest) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM active_quests WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean hasCompletedQuest(Player player, Object quest) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM completed_quests WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean questOnRecord(Player player, Object quest){
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quests WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM active_quests WHERE `uuid` = '" + player.getUniqueId() + "' AND `name` = '" + quest + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                if(resultSet1.next()){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
