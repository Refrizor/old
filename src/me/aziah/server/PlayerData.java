package me.aziah.server;

import me.aziah.ranks.Rank;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private static final HashMap<String, Integer> staffBranchID = new HashMap<>();
    private static final HashMap<String, Integer> donorBranchID = new HashMap<>();
    private static final HashMap<String, Integer> builderBranchID = new HashMap<>();
    private static final HashMap<String, Integer> affiliateBranchID = new HashMap<>();

    private static final HashMap<UUID, String> location = new HashMap<>();

    private static final List<String> muted = new ArrayList<>();

    private static int id;

    public static Integer getBranchNumber(OfflinePlayer player, String branchName) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `" + branchName + "` FROM ranks WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }else {
                id = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public static boolean isMuted(OfflinePlayer player){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM active_mutes WHERE `uuid` = ?");
            preparedStatement.setString(1, String.valueOf(player.getUniqueId()));
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static int getStaffBranchID(Player player) {
        return staffBranchID.get(player.getName());
    }

    public static int getDonorBranchID(Player player) {
        return donorBranchID.get(player.getName());
    }

    public static int getBuilderBranchID(Player player) {
        return builderBranchID.get(player.getName());
    }

    public static int getAffiliateBranchID(Player player){
        return affiliateBranchID.get(player.getName());
    }

    public static HashMap<String, Integer> getStaffBranch() {
        return staffBranchID;
    }

    public static HashMap<String, Integer> getDonorBranch() {
        return donorBranchID;
    }

    public static HashMap<String, Integer> getAffiliateBranch() {
        return affiliateBranchID;
    }

    public static HashMap<String, Integer> getBuilderBranch() {
        return builderBranchID;
    }

    public static HashMap<UUID, String> getLocation() {
        return location;
    }

    public static List<String> getMuted() {
        return muted;
    }
}