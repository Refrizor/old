package me.aziah.midstforth;

import me.aziah.server.DatabaseHandler;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Economy {

    public static void set(Player player, int amount) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `midstforth_economy` SET `amount`=" + amount + " WHERE `uuid` = '" + player.getUniqueId() + "'");
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void add(Player player, int amount) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT amount FROM `midstforth_economy` WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            int getAmount = 0;
            if (resultSet.next()) {
                getAmount = resultSet.getInt(1);
            }

            int finalAmount = getAmount + amount;
            PreparedStatement update = connection.prepareStatement("UPDATE `midstforth_economy` SET `amount`= " + finalAmount + " WHERE `uuid` = '" + player.getUniqueId() + "'");
            update.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void remove(Player player, int amount) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT amount FROM `midstforth_economy` WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            int getAmount = 0;
            if (resultSet.next()) {
                getAmount = resultSet.getInt(1);
            }

            int finalAmount = getAmount - amount;
            PreparedStatement update = connection.prepareStatement("UPDATE `midstforth_economy` SET `amount`= " + finalAmount + " WHERE `uuid` = '" + player.getUniqueId() + "'");
            update.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insert(Player player) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM midstforth_economy WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                PreparedStatement insert = connection.prepareStatement("INSERT INTO `midstforth_economy`(`uuid`, `amount`) VALUES ('" + player.getUniqueId() + "', 100)");
                insert.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer retrieve(Player player){
        int coins = 0;
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT amount FROM midstforth_economy WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                coins = resultSet.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return coins;
    }
}
