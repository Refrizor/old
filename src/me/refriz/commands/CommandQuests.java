package me.refriz.commands;

import me.refriz.server.DatabaseConnector;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandQuests implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        try{
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM quests WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder stringBuilder = new StringBuilder(); 
            while(resultSet.next()){
                String quests = resultSet.getString(1);
                stringBuilder.append(ChatColor.AQUA).append(quests).append(ChatColor.RESET).append(", ");
            }
            String quests = stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).toString();

            player.sendMessage(ChatColor.GREEN + "--- QUEST LIST ---\n" + quests);
        }catch(Exception e){
            player.sendMessage(ChatColor.RED + "No quests found!");
        }
        return true;
    }
}
