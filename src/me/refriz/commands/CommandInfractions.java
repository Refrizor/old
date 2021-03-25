package me.refriz.commands;

import me.refriz.server.DatabaseConnector;
import me.refriz.server.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandInfractions implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if(PlayerData.getStaffBranchID().get(player.getName()) >=2){
            int length = args.length;

            if(length == 1){
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

                try{
                    Connection connection = DatabaseConnector.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT type, reason FROM `infractions` WHERE `uuid` = '" + offlinePlayer.getUniqueId() + "'");
                    ResultSet resultSet = preparedStatement.executeQuery();

                    player.sendMessage(ChatColor.AQUA + "List of infractions:");

                    while(resultSet.next()){
                        player.sendMessage(resultSet.getString(1) + ", reason: " + resultSet.getString(2));
                    }
                }catch(Exception e){

                }
            }
        }
        return true;
    }
}
