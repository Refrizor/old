package me.aziah.commands;

import me.aziah.server.DatabaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandProgress implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT `name` FROM `completed_quests` WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                String quests = resultSet.getString(1);
                stringBuilder.append(ChatColor.AQUA).append(quests).append(ChatColor.RESET).append(", ");
            }
            String quests = stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).toString();

            player.sendMessage(ChatColor.GOLD + "✔ Completed quests\n" + quests);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "You have not finished any quests yet!");
        }
        return true;
    }
}
