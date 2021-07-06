package me.aziah.midstforth;

import me.aziah.midstforth.quests.list.QuestIntro;
import me.aziah.server.DatabaseHandler;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Midstforth {

    //TODO: only insert in played_midstforth after intro sequence completion

    public static void startNewGame(Player player){
        new Cinematic().intro(player);
    }

    public static boolean hasPlayed(Player player) {
        try {
            Connection connection = DatabaseHandler.getConnection();
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
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `played_midstforth`(`uuid`) VALUES ('" + player.getUniqueId() + "')");
            preparedStatement.execute();

            PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `midstforth_economy`(`uuid`, `amount`) VALUES ('" + player.getUniqueId() + "', 0)");
            preparedStatement1.execute();

            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO `locations`(`uuid`, `type`) VALUES ('" + player.getUniqueId() + "', 'PLACEHOLDER')");
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO `progression_states`(`uuid`, `discover_lab`, `lab_power`) VALUES ('" + player.getUniqueId() + "', 0, 0)");
            preparedStatement3.execute();

            startNewGame(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getEngine(Player player){
        int engine = 0;
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT type FROM `engines` WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                engine = resultSet.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return engine;
    }

    public static String getLocation(Player player){
        String location = null;

        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT type FROM locations WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                location = resultSet.getString(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public static void updateLocation(Player player, Object location){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `locations` SET `type`= '" + location + "' WHERE `uuid` = '" + player.getUniqueId() + "'");
            preparedStatement.execute();
            player.sendMessage("Now entering " + location);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}