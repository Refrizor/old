package me.refriz.infractions;

import me.refriz.server.DatabaseConnector;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Ban {

    public static void init(Player player, String bannedBy, String reason){

        if(player.isOnline()){
            player.kickPlayer(ChatColor.RED+ "You are banned!\n\n" + ChatColor.WHITE + "Reason: " + ChatColor.RED + reason);
        }

        try{
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM active_bans WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                PreparedStatement insert = connection.prepareStatement("INSERT INTO `active_bans`(`uuid`, `reason`, `banned_by`) VALUES ('" + player.getUniqueId() + "', '" + reason + "', '" + bannedBy + "')");
                insert.execute();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void checkBan(Player player){
        String reason = null;
        try{
            Connection connection = DatabaseConnector.getConnection();
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
