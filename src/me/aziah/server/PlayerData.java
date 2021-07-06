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

public class PlayerData {

    private static final HashMap<String, Integer> staffBranchID = new HashMap<>();
    private static final HashMap<String, Integer> donorBranchID = new HashMap<>();
    private static final HashMap<String, Integer> builderBranchID = new HashMap<>();

    private static final List<String> muted = new ArrayList<>();

    private static int id;

    public static Integer getBranchNumber(Player player, String branchName) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `" + branchName + "` FROM ranks WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public static boolean getRank(List<Player> rank, Player player) {

        if (rank == Rank.ADMIN.getRank()) {

            return rank.contains(player);
        }
        if (rank == Rank.MOD.getRank()) {

            return rank.contains(player);
        }
        if (rank == Rank.HELPER.getRank()) {

            return rank.contains(player);
        }
        if (rank == Rank.DONOR.getRank()) {

            return rank.contains(player);
        }
        if (rank == Rank.NONE.getRank()) {
            return rank.contains(player);
        }
        return true;
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

    public static HashMap<String, Integer> getStaffBranch() {
        return staffBranchID;
    }


    public static HashMap<String, Integer> getDonorBranch() {
        return donorBranchID;
    }

    public static int getBuilderBranchID(Player player) {
        return builderBranchID.get(player.getName());
    }

    public static HashMap<String, Integer> getBuilderBranch() {
        return builderBranchID;
    }

    public static List<String> getMuted() {
        return muted;
    }
}