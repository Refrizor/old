package me.aziah.events;

import me.aziah.midstforth.States;
import me.aziah.ranks.Rank;
import me.aziah.server.Messages;
import me.aziah.server.PlayerData;
import me.aziah.server.PlayerStates;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        quitMessage(player, event);

        States.getDiscoveredLab().remove(player.getName());
        States.getPowerLab().remove(player.getName());
        States.getApartment().remove(player.getName());

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

        if (PlayerData.getStaffBranchID(player) == 3) {
            event.setQuitMessage(Rank.ADMIN.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "left");
        }

        if (PlayerData.getDonorBranchID(player) == 0) {

            if (PlayerData.getStaffBranchID(player) == 1) {
                event.setQuitMessage(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "left");
            }
            if (PlayerData.getStaffBranchID(player) == 2) {
                event.setQuitMessage(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "left");
            }

        } else {
            /*
            Else if they do have a donor rank:
             */

            if (PlayerData.getDonorBranchID(player) == 1) {
                if (PlayerData.getStaffBranchID(player) == 0) {
                    event.setQuitMessage(Rank.DONOR.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "left");
                }
            }
            if (PlayerData.getDonorBranchID(player) == 2) {
                if (PlayerData.getStaffBranchID(player) == 0) {
                    event.setQuitMessage(Rank.DONOR2.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "left");
                }
            }

            if (PlayerData.getStaffBranchID(player) == 1) {
                event.setQuitMessage(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "left");
            }
            if (PlayerData.getStaffBranchID(player) == 2) {
                event.setQuitMessage(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "left");
            }
        }

        if (PlayerData.getStaffBranchID(player) == 0 && PlayerData.getDonorBranchID(player) == 0) {
            event.setQuitMessage(Rank.NONE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + "left");
        }
    }
}