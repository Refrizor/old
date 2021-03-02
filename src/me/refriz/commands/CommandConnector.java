package me.refriz.commands;

import me.refriz.server.DatabaseConnector;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;

public class CommandConnector implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;

        try{
            Connection connection = DatabaseConnector.getConnection();
            if(!connection.isClosed()){
                player.sendMessage(ChatColor.GREEN + "Connection successful");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
