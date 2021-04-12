package me.refriz.events;

import me.refriz.minigames.pvp.PVPGame;
import me.refriz.ranks.Rank;
import me.refriz.server.Messages;
import me.refriz.server.PlayerData;
import me.refriz.server.States;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        quitMessage(player, event);

        States.getLobby().remove(player.getName());

        Rank.ADMIN.getRank().remove(player);
        Rank.MOD.getRank().remove(player);
        Rank.HELPER.getRank().remove(player);
        Rank.DONOR.getRank().remove(player);
        Rank.NONE.getRank().remove(player);

        PlayerData.getStaffBranch().remove(player.getName());
        PlayerData.getDonorBranch().remove(player.getName());
        PlayerData.getBuilderBranch().remove(player.getName());

        States.getVanish().remove(player.getName());
        States.getApartment().remove(player.getName());
        PVPGame.getQueue().remove(player.getName());
        PVPGame.getIngame().remove(player);
        PVPGame.getGracePeriod().remove(player.getName());

        PlayerData.getMuted().remove(player.getName());
    }

    public static void quitMessage(Player player, PlayerQuitEvent event) {

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