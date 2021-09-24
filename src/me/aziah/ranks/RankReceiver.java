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
    int affiliate;

    public void receive(Player player){

        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT donor,staff,builder,affiliate FROM ranks WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                donor = resultSet.getInt(1);
                staff = resultSet.getInt(2);
                builder = resultSet.getInt(3);
                affiliate = resultSet.getInt(4);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        /*
        Puts players in their correlating branch #

        For whatever the queries above are
         */

        PlayerData.getDonorBranch().put(player.getName(), donor);
        PlayerData.getStaffBranch().put(player.getName(), staff);
        PlayerData.getBuilderBranch().put(player.getName(), builder);
        PlayerData.getAffiliateBranch().put(player.getName(), affiliate);

        new Permission().deployPermissions(player);
        new Tab().deploy(player);
    }
}
