package me.aziah.commands;

import me.aziah.ranks.Rank;
import me.aziah.server.DatabaseHandler;
import me.aziah.server.SQLHandler;
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

public class CommandWhois implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        String message = "";
        for(int i = 1; i < args.length; i++){
            message = message + args[i] + " ";
        }

        if(length == 1){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

            try{
                Connection connection = DatabaseHandler.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT message FROM `whois` WHERE `uuid` = '" + offlinePlayer.getUniqueId() + "'");
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){

                    player.sendMessage(ChatColor.AQUA + "About " + offlinePlayer.getName());
                    player.sendMessage("");
                    player.sendMessage(ChatColor.WHITE + "Rank: " + Rank.getRankTag(offlinePlayer));
                    player.sendMessage(ChatColor.GRAY + "Whois message: " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', resultSet.getString(1)));
                }else{
                    player.sendMessage(ChatColor.AQUA + "About " + offlinePlayer.getName());
                    player.sendMessage("");
                    player.sendMessage(ChatColor.WHITE + "Rank: " + Rank.getRankTag(offlinePlayer));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(length >= 2) {
            if (args[0].equalsIgnoreCase("set")) {
                try {
                    Connection connection = DatabaseHandler.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM whois WHERE `uuid` = '" + player.getUniqueId() + "'");
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if(!resultSet.next()){
                        SQLHandler.action("INSERT INTO `whois`(`uuid`, `message`) VALUES('" + player.getUniqueId() + "', '" + message + "')");
                        player.sendMessage(ChatColor.GREEN + "Whois created!");
                    }else{
                        SQLHandler.action("UPDATE `whois` SET `message` = '" + message + "' WHERE `uuid` = '" + player.getUniqueId() + "'");
                        player.sendMessage(ChatColor.GREEN + "Whois updated!");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    player.sendMessage(ChatColor.RED + "An error occurred while setting your whois: " + e.getMessage());
                }
            }
        }
        return true;
    }
}
