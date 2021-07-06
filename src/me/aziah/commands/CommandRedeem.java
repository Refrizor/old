package me.aziah.commands;

import me.aziah.Coins;
import me.aziah.midstforth.Economy;
import me.aziah.ranks.Rank;
import me.aziah.server.DatabaseHandler;
import me.aziah.server.Messages;
import me.aziah.server.SQLHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class CommandRedeem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        if (length == 0 || length > 3) {
            player.sendMessage(ChatColor.RED + "/redeem <code>");
        }

        if (length == 1) {
            String code = args[0];

            try {
                Connection connection = DatabaseHandler.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT type FROM `codes` WHERE `code` = '" + code + "'");
                ResultSet resultSet = preparedStatement.executeQuery();

                String type;

                if (resultSet.next()) {
                    type = resultSet.getString(1);

                    player.sendMessage(ChatColor.GREEN + "Code redeemed!");
                    check(type, player);

                    SQLHandler.action("DELETE FROM `codes` WHERE `code` = '" + code + "'");
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid code");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(length == 3){
            if(args[0].equalsIgnoreCase("add")) {
                String type = args[1];
                String code = args[2];

                SQLHandler.action("INSERT INTO `codes`(`code`, `type`) VALUES ('" + code + "', '" + type + "')");

                player.sendMessage(ChatColor.GREEN + "Generated code! " + ChatColor.GRAY + "[" + type + ", " + code + "]");
            }
        }
        return true;
    }

    private void check(String type, Player player) {
        if (type.equals("donor")) {
            Rank.update(player, "donor", 1);
            player.sendMessage(Messages.SPECIAL.getMessage() + ChatColor.GREEN + "Unlocked " + Rank.DONOR.getPrefix());

        } else if (type.equals("coins")) {
            int min = 10;
            int max = 20;

            Random random = new Random();
            int value = random.nextInt(max - min + 1) + min;

            Coins.add(player, value);
            player.sendMessage(Messages.SPECIAL.getMessage() + ChatColor.GREEN + "Received " + ChatColor.YELLOW + value + " coins!");

        } else if (type.equals("money")) {
            int min = 10;
            int max = 20;

            Random random = new Random();
            int value = random.nextInt(max - min + 1) + min;

            Economy.add(player, value);
            player.sendMessage(Messages.SPECIAL.getMessage() + ChatColor.GREEN + "Received " + ChatColor.YELLOW + value + " IC " + ChatColor.AQUA + "(Midstforth)!");
        }
    }

    private static int getRandom(int max){
        return (int) (Math.random() * max);
    }
}