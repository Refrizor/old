package me.aziah.commands;

import me.aziah.midstforth.quests.Quest;
import me.aziah.server.DatabaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandQuest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        if(length == 0){
            try{
                Connection connection = DatabaseHandler.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT name,description FROM `active_quests` WHERE `uuid` = '" + player.getUniqueId() + "'");
                ResultSet resultSet = preparedStatement.executeQuery();

                String quest;
                String description;

                if(resultSet.next()){
                    quest = resultSet.getString(1);
                    description = resultSet.getString(2);
                    player.sendMessage(ChatColor.YELLOW + "Current quest: " + ChatColor.AQUA + quest);
                    player.sendMessage(ChatColor.GRAY + "Description: " + ChatColor.WHITE + description);
                }else{
                    player.sendMessage(ChatColor.RED + "You have no quest assigned. Assign with: " + ChatColor.AQUA + "/quest assign <quest>");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(length == 2){
            if(args[0].equalsIgnoreCase("assign")){
                Quest.assignQuest(player, args[1], true);
            }
            if(args[0].equalsIgnoreCase("remove")){
                Quest.unassignQuest(player, args[1]);
            }
        }
        return true;
    }
}
