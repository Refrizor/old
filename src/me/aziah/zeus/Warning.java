package me.aziah.zeus;

import me.aziah.server.DatabaseHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Warning {

    public static void add(Player punisher, String reason, OfflinePlayer target){
        try{
            Connection connector = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connector.prepareStatement("INSERT INTO `warnings`(`uuid`, `reason`, `warned_by`) VALUES ('" + target.getUniqueId() + "', '" + reason + "', '" + punisher.getUniqueId() + "')");
            PreparedStatement preparedStatement1 = connector.prepareStatement("INSERT INTO `infractions`(`uuid`, `type`, `reason`, `moderator`) VALUES ('" + target.getUniqueId() + "', 'Warning', '" + reason + "', '" + punisher.getUniqueId() + "')");

            preparedStatement.execute();
            preparedStatement1.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}