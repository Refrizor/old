package me.refriz.events;

import me.refriz.ranks.Permission;
import me.refriz.ranks.Tab;
import me.refriz.server.DatabaseConnector;
import me.refriz.ranks.Rank;
import me.refriz.server.PlayerData;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RankReceiver {

    int donor;
    int staff;
    int builder;

    public void receive(Player player){

        try{
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement donorStatement = connection.prepareStatement("SELECT donor FROM ranks WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet getDonor = donorStatement.executeQuery();

            if(getDonor.next()){
                donor = getDonor.getInt(1);
            }

            PreparedStatement staffStatement = connection.prepareStatement("SELECT staff FROM ranks WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet getStaff = staffStatement.executeQuery();

            if(getStaff.next()){
                staff = getStaff.getInt(1);
            }

            PreparedStatement builderStatement = connection.prepareStatement("SELECT builder FROM ranks WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet getBuilder = builderStatement.executeQuery();

            if(getBuilder.next()){
                builder = getBuilder.getInt(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        if(staff == 1){
            Rank.HELPER.getRank().add(player);
        }
        if(staff == 2){
            Rank.MOD.getRank().add(player);
        }
        if(staff == 3){
            Rank.ADMIN.getRank().add(player);
        }

        if(donor == 1){
            Rank.DONOR.getRank().add(player);
        }

        if(builder == 1){
            Rank.BUILDER.getRank().add(player);
        }

        if(staff == 0 && donor == 0){
            Rank.NONE.getRank().add(player);
        }

        /*
        Puts players in their correlating branch #

        For whatever the queries above are
         */

        PlayerData.getDonorBranchID().put(player.getName(), donor);
        PlayerData.getStaffBranchID().put(player.getName(), staff);
        PlayerData.getBuilderBranchID().put(player.getName(), builder);

        new Permission().deployPermissions(player);
        new Tab().deploy(player);
    }
}
