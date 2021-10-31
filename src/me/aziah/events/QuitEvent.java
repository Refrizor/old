package me.aziah.events;

import me.aziah.midstforth.States;
import me.aziah.ranks.Rank;
import me.aziah.server.Messages;
import me.aziah.server.PlayerData;
import me.aziah.server.PlayerStates;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        quitMessage(player, event);

        States.getDiscoveredLab().remove(player.getUniqueId());
        States.getPowerLab().remove(player.getUniqueId());
        States.getApartment().remove(player.getName());
        States.getDiscoveries().remove(player.getUniqueId());
        States.getSatellite1().remove(player.getUniqueId());

        PlayerStates.getLobby().remove(player.getName());
        PlayerStates.getVanish().remove(player.getName());

        PlayerData.getStaffBranch().remove(player.getName());
        PlayerData.getDonorBranch().remove(player.getName());
        PlayerData.getBuilderBranch().remove(player.getName());
        PlayerData.getAffiliateBranch().remove(player.getName());


        PlayerData.getMuted().remove(player.getName());
        PlayerData.getLocation().remove(player.getUniqueId());
    }

    private static void quitMessage(Player player, PlayerQuitEvent event) {

        int donorLevel = PlayerData.getDonorBranchID(player);
        int staffLevel = PlayerData.getStaffBranchID(player);
        int builderLevel = PlayerData.getBuilderBranchID(player);
        int affiliateLevel = PlayerData.getAffiliateBranchID(player);

        switch(staffLevel){
            case 3:
                event.setQuitMessage(Rank.ADMIN.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " left");
                break;
            case 2:
                event.setQuitMessage(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " left");
                break;
            case 1:
                event.setQuitMessage(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " left");
                break;
        }

        switch (donorLevel) {
            case 2:
                if (staffLevel == 0) {
                    event.setQuitMessage(Rank.DONOR2.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " left");
                }
                break;
            case 1:
                if (staffLevel == 0) {
                    event.setQuitMessage(Rank.DONOR.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " left");
                }
            case 0:
                if (staffLevel == 0) {
                    event.setQuitMessage(Rank.NONE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " left");
                    break;
                }
        }

        switch (affiliateLevel) {
            case 1:
                if (staffLevel == 0) {
                    event.setQuitMessage(Rank.AFFILIATE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + ChatColor.GRAY + " left");
                    break;
                }
        }
    }
}