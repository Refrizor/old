package me.aziah.midstforth;

import me.aziah.server.DatabaseHandler;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class States {

    private static List<String> discoveredLab = new ArrayList<>();
    private static List<String> powerLab = new ArrayList<>();

    private static final List<String> apartment = new ArrayList<>();

    public static List<String> getApartment() {
        return apartment;
    }

    public static List<String> getDiscoveredLab() {
        return discoveredLab;
    }

    public static List<String> getPowerLab() {
        return powerLab;
    }

    public static boolean hasDiscoveredLab(Player player){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `discover_lab` FROM `progression_states` WHERE `uuid` = '" + player.getUniqueId() + "'");
            int id = 0;

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }

            if(id == 1){
                return true;

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean hasPower(Player player){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `lab_power` FROM `progression_states` WHERE `uuid` = '" + player.getUniqueId() + "'");
            int id = 0;

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }

            if(id == 1){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
