package me.refriz.midstforth;

import me.refriz.server.DatabaseConnector;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Midstforth {

    public static boolean hasPlayed(Player player) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM played_midstforth WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insert(Player player) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `played_midstforth`(`uuid`) VALUES ('" + player.getUniqueId() + "')");
            preparedStatement.execute();

            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `midstforth_economy`(`uuid`, `amount`) VALUES ('" + player.getUniqueId() + "', 0)");
            preparedStatement1.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getEngineType(Player player) {
        int engine = 0;

        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT type FROM `engines` WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                engine = resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return engine;
    }
}