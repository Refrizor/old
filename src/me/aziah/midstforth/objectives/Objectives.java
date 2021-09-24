package me.aziah.midstforth.objectives;

import me.aziah.server.DatabaseHandler;
import me.aziah.server.PlayerUtils;
import me.aziah.server.SQLHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Objectives {

    public static void addObjective(Player player, Object id, Object name, Object description){
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `objectives` WHERE `uuid` = '" + player.getUniqueId() + "' AND `id` = '" + id + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()){
                SQLHandler.action("INSERT INTO `objectives`(`uuid`, `id`, `name`, `description`) VALUES ('" + player.getUniqueId() + "', '" + id + "', '" + name + "', '" + description + "')");

                PlayerUtils.sendTitle(player, ChatColor.YELLOW + "New objective", ChatColor.AQUA + "" + name);

                player.sendMessage(ChatColor.YELLOW + "NEW OBJECTIVE: " + ChatColor.AQUA + name + "\n\n" + ChatColor.GREEN + description);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeObjective(Player player, Object id){
        SQLHandler.action("DELETE FROM `objectives` WHERE `uuid` = '" + player.getUniqueId() + "' AND `id` = '" + id + "'");
    }

    public static void removeAll(Player player){
        SQLHandler.action("DELETE FROM `objectives` WHERE `uuid` = '" + player.getUniqueId() + "'");
    }


}