package me.refriz.zeus;

import me.refriz.server.DatabaseConnector;
import me.refriz.server.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

public class Mute {

    public static void mute(Player punisher, String reason, OfflinePlayer target) {
        if (!PlayerData.isMuted(target)) {
            try {
                Connection connection = DatabaseConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `active_mutes`(`uuid`, `reason`, `muted_by`) VALUES ('" + target.getUniqueId() + "', '" + reason + "', '" + punisher.getUniqueId() + "')");
                PreparedStatement preparedStatement1 = connection.prepareStatement("INSERT INTO `infractions`(`uuid`, `type`, `reason`, `moderator`) VALUES ('" + target.getUniqueId() + "', 'Mute', '" + reason + "', '" + punisher.getUniqueId() + "')");

                preparedStatement.execute();
                preparedStatement1.execute();

                if (target.isOnline()) {
                    if (!PlayerData.getMuted().contains(target.getName())) {
                        PlayerData.getMuted().add(target.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void unmute(OfflinePlayer player) {
        try {
            Connection connection = DatabaseConnector.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `active_mutes` WHERE `uuid` = '" + player.getUniqueId() + "'");
            preparedStatement.execute();
        } catch (Exception e) {
        }
    }
}