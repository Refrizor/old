package me.aziah.midstforth;

import me.aziah.server.DatabaseHandler;
import me.aziah.server.SQLHandler;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class States {

    private static HashMap<UUID, Boolean> discoveredLab = new HashMap<>();
    private static HashMap<UUID, Boolean> powerLab = new HashMap<>();
    private static HashMap<UUID, String> discoveries = new HashMap<>();

    private static final List<UUID> satellite1 = new ArrayList<>();
    private static final List<String> apartment = new ArrayList<>();

    public static List<String> getApartment() {
        return apartment;
    }

    public static HashMap<UUID, Boolean> getDiscoveredLab() {
        return discoveredLab;
    }

    public static HashMap<UUID, Boolean> getPowerLab() {
        return powerLab;
    }

    public static HashMap<UUID, String> getDiscoveries() {
        return discoveries;
    }

    public static List<UUID> getSatellite1() {
        return satellite1;
    }

    public static void setState(Player player, String name, int state){
        String set = "`" + name + "`";
        SQLHandler.action("UPDATE `progression_states` SET " + set + "= " + state + " WHERE `uuid` = '" + player.getUniqueId() + "'");
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
