package me.refriz.zeus;

import me.refriz.server.DatabaseConnector;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Warning {

    public static void add(Player punisher, String reason, OfflinePlayer target){
        try{
            Connection connector = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connector.prepareStatement("INSERT INTO `warnings`(`uuid`, `reason`, `warned_by`) VALUES ('" + target.getUniqueId() + "', '" + reason + "', '" + punisher.getUniqueId() + "')");
            PreparedStatement preparedStatement1 = connector.prepareStatement("INSERT INTO `infractions`(`uuid`, `type`, `reason`, `moderator`) VALUES ('" + target.getUniqueId() + "', 'Warning', '" + reason + "', '" + punisher.getUniqueId() + "')");

            preparedStatement.execute();
            preparedStatement1.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


// right syntax to use near '7d16b15d-bb22-4a6d-80db-6213b3d75007')' at line 1