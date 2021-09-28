package me.aziah.midstforth;

import me.aziah.events.Cinematics;
import me.aziah.midstforth.quests.list.QuestIntro;
import me.aziah.server.DatabaseHandler;
import me.aziah.server.PlayerData;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Midstforth {

    //TODO: only insert in played_midstforth after intro sequence completion

    public static void startNewGame(Player player){
        new Cinematics().introduction(player);
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

            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO `locations`(`uuid`, `name`) VALUES ('" + player.getUniqueId() + "', 'Placeholder')");
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

    public static void cache(Player player){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement progression = connection.prepareStatement("SELECT discover_lab, lab_power FROM `progression_states` WHERE `uuid` = '" + player.getUniqueId() + "'");
            PreparedStatement location = connection.prepareStatement("SELECT name FROM locations WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet resultSet = progression.executeQuery();
            int discoverLab = 0;
            int labPower = 0;
            if(resultSet.next()){
                discoverLab = resultSet.getInt(1);
                labPower = resultSet.getInt(2);
            }
            if(discoverLab == 1){
                States.getDiscoveredLab().put(player.getUniqueId(), true);
            }else{
                States.getDiscoveredLab().put(player.getUniqueId(), false);
            }

            if(labPower == 1) {
                States.getPowerLab().put(player.getUniqueId(), true);
            }else {
                States.getPowerLab().put(player.getUniqueId(), false);
            }

            ResultSet resultSet1 = location.executeQuery();
            if(resultSet1.next()){
                String locationStr = resultSet1.getString(1);
                PlayerData.getLocation().put(player.getUniqueId(), locationStr);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}