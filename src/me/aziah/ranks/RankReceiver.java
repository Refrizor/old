package me.aziah.ranks;

import me.aziah.server.DatabaseHandler;
import me.aziah.server.PlayerData;
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
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT donor,staff,builder FROM ranks WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                donor = resultSet.getInt(1);
                staff = resultSet.getInt(2);
                builder = resultSet.getInt(3);
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

        PlayerData.getDonorBranch().put(player.getName(), donor);
        PlayerData.getStaffBranch().put(player.getName(), staff);
        PlayerData.getBuilderBranch().put(player.getName(), builder);

        new Permission().deployPermissions(player);
        new Tab().deploy(player);
    }
}
