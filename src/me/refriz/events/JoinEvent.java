package me.refriz.events;

import me.refriz.zeus.Ban;
import me.refriz.Lobby;
import me.refriz.Coins;
import me.refriz.Inferris;
import me.refriz.midstforth.Midstforth;
import me.refriz.ranks.Rank;
import me.refriz.server.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        new Lobby().send(player);

        if(!Midstforth.hasPlayed(player)){
            Midstforth.insert(player);
        }

        if(PlayerData.isMuted(player)){
            PlayerData.getMuted().add(player.getName());
        }


        try {
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ranks WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                new RankReceiver().receive(player);

            } else {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO `ranks`(`uuid`, `donor`, `staff`, `builder`) VALUES ('" + player.getUniqueId() + "', 0, 0, 0)");
                insert.execute();
                player.sendMessage(ChatColor.GREEN + "Welcome!");

                Coins.insert(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Ban.checkBan(player);
        joinMessage(player, event);
    }

    public static void joinMessage(Player player, PlayerJoinEvent event) {

        if (PlayerData.getStaffBranchID().get(player.getName()) == 3) {
            event.setJoinMessage(Rank.ADMIN.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "joined");
        }

        if (PlayerData.getDonorBranchID().get(player.getName()) == 0) {

            if (PlayerData.getStaffBranchID().get(player.getName()) == 1) {
                event.setJoinMessage(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "joined");
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 2) {
                event.setJoinMessage(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "joined");
            }
        } else {

            /*
            Donor section
             */

            if (PlayerData.getDonorBranchID().get(player.getName()) == 1) {
                if (PlayerData.getStaffBranchID().get(player.getName()) == 0) {
                    event.setJoinMessage(Rank.DONOR.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "joined");
                }
            }
            if (PlayerData.getDonorBranchID().get(player.getName()) == 2) {
                if (PlayerData.getStaffBranchID().get(player.getName()) == 0) {
                    event.setJoinMessage(Rank.DONOR2.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "joined");
                }
            }

            if (PlayerData.getStaffBranchID().get(player.getName()) == 1) {
                event.setJoinMessage(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "joined");
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 2) {
                event.setJoinMessage(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "joined");
            }
        }

        if(!player.isOp()){
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            if (States.getVanish().contains(all.getName())) {
                    player.hidePlayer(Inferris.getInstance(), all);
                }
            }
        }
    }
}