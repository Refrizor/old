package me.aziah.commands;

import me.aziah.ranks.RankReceiver;
import me.aziah.ranks.Rank;
import me.aziah.ranks.Tab;
import me.aziah.server.DatabaseHandler;
import me.aziah.server.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommandSetrank implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;

        if (player.isOp()) {
            int length = args.length;

            if (length < 3) {
                player.sendMessage(ChatColor.RED + "/setrank <player> <branch> <number>");
            }
            if (length == 3) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                String branch = args[1];
                String id = args[2];

                try {
                    Connection connection = DatabaseHandler.getConnection();
                    PreparedStatement inDbStatement = connection.prepareStatement("SELECT * FROM ranks WHERE uuid = '" + target.getUniqueId() + "'");
                    ResultSet inDatabase = inDbStatement.executeQuery();

                    if (inDatabase.next()) {
                        PreparedStatement update = connection.prepareStatement("UPDATE `ranks` SET `" + branch + "` = " + id + " WHERE `uuid` = '" + target.getUniqueId() + "'");
                        update.execute();

                        PlayerData.getDonorBranch().remove(target.getName());
                        PlayerData.getStaffBranch().remove(target.getName());
                        PlayerData.getBuilderBranch().remove(target.getName());

                        new Tab().removeEntries(target);
                        new RankReceiver().receive(target);

                        player.sendMessage(ChatColor.GREEN + "Rank set!");
                    }else{
                        player.sendMessage(ChatColor.RED + "Could not find that player in the database!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    player.sendMessage(ChatColor.RED + "An error has occurred. " + e.getMessage());
                    if(e.getMessage() == null){
                        player.sendMessage(ChatColor.RED + "Uh oh, looks like it returned null. Is that player online?");
                    }
                }
            }
        }
        return true;
    }
}