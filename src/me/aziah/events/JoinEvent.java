package me.aziah.events;

import me.aziah.midstforth.States;
import me.aziah.ranks.RankReceiver;
import me.aziah.zeus.Ban;
import me.aziah.lobby.Lobby;
import me.aziah.Coins;
import me.aziah.Inferris;
import me.aziah.midstforth.Midstforth;
import me.aziah.ranks.Rank;
import me.aziah.server.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class JoinEvent implements Listener {

    ArrayList<UUID> array = new ArrayList<>();
    HashMap<UUID, UUID> party = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();


        //new Lobby().send(player, true);

        if(!Midstforth.hasPlayed(player)){
            Midstforth.insert(player);
        }

        if(PlayerData.isMuted(player)){
            PlayerData.getMuted().add(player.getName());
        }

        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ranks WHERE uuid = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                new RankReceiver().receive(player);

            } else {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO `ranks`(`uuid`, `donor`, `staff`, `builder`, `affiliate`) VALUES ('" + player.getUniqueId() + "', 0, 0, 0, 0)");
                insert.execute();
                player.sendMessage(ChatColor.GREEN + "Welcome!");

                Coins.insert(player);
                new RankReceiver().receive(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Ban.checkBan(player);
        joinMessage(player, event);
        Midstforth.cache(player);


        array.add(player.getUniqueId());

        JoinEvent joinEvent = new JoinEvent();
        player.sendMessage(String.valueOf(joinEvent.array.size()));

    }

    private static void joinMessage(Player player, PlayerJoinEvent event) {

        int donorLevel = PlayerData.getDonorBranchID(player);
        int staffLevel = PlayerData.getStaffBranchID(player);
        int builderLevel = PlayerData.getBuilderBranchID(player);
        int affiliateLevel = PlayerData.getAffiliateBranchID(player);

        switch(staffLevel){
            case 3:
                event.setJoinMessage(Rank.ADMIN.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " joined");
                break;
            case 2:
                event.setJoinMessage(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " joined");
                break;
            case 1:
                event.setJoinMessage(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " joined");
                break;
        }

        switch (donorLevel) {
            case 2:
                if (staffLevel == 0) {
                    event.setJoinMessage(Rank.DONOR2.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " joined");
                }
                break;
            case 1:
                if (staffLevel == 0) {
                    event.setJoinMessage(Rank.DONOR.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " joined");
                }
            case 0:
                if (staffLevel == 0) {
                    event.setJoinMessage(Rank.NONE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " joined");
                    break;
                }
        }

        switch (affiliateLevel) {
            case 1:
                if (staffLevel == 0) {
                    event.setJoinMessage(Rank.AFFILIATE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " joined");
                    break;
                }
        }

        if(!player.isOp()){
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            if (PlayerStates.getVanish().contains(all.getName())) {
                    player.hidePlayer(Inferris.getInstance(), all);
                }
            }
        }
    }
}