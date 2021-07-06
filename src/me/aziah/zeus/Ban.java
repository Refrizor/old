package me.aziah.zeus;

import me.aziah.server.DatabaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Ban {

    public static void add(Player punisher, String reason, OfflinePlayer target){

        if(target.isOnline()){
            Player onlineTarget = Bukkit.getPlayer(target.getUniqueId());
            onlineTarget.kickPlayer(ChatColor.RED+ "You are banned!\n\n" + ChatColor.WHITE + "Reason: " + ChatColor.RED + reason);
        }

        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM active_bans WHERE `uuid` = '" + target.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                PreparedStatement insert = connection.prepareStatement("INSERT INTO `active_bans`(`uuid`, `reason`, `banned_by`) VALUES ('" + target.getUniqueId() + "', '" + reason + "', '" + punisher.getUniqueId() + "')");
                PreparedStatement insert1 = connection.prepareStatement("INSERT INTO `infractions`(`uuid`, `type`, `reason`, `moderator`) VALUES ('" + target.getUniqueId() + "', 'Ban', '" + reason + "', '" + punisher.getUniqueId() + "')");

                insert.execute();
                insert1.execute();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void remove(OfflinePlayer target){

        try{
            Connection connection = DatabaseHandler.getConnection();
                PreparedStatement insert = connection.prepareStatement("DELETE FROM `active_bans` WHERE `uuid` = '" + target.getUniqueId() + "'");
                insert.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isBanned(OfflinePlayer player){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `active_bans` WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void checkBan(Player player){
        String reason = null;
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM active_bans WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT `reason` FROM active_bans WHERE `uuid` = '" + player.getUniqueId() + "'");
                ResultSet resultSet1 = preparedStatement1.executeQuery();

                if(resultSet1.next()){
                    reason = resultSet1.getString(1);
                }
                player.kickPlayer(ChatColor.RED+ "You are banned!\n\n" + ChatColor.WHITE + "Reason: " + ChatColor.RED + reason);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
