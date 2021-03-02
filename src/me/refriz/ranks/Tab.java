package me.refriz.ranks;

import me.refriz.server.PlayerData;
import org.bukkit.entity.Player;

import static me.refriz.Inferris.*;

public class Tab {

    public void deploy(Player player){

        if(PlayerData.getDonorBranchID().get(player.getName()) == 0) {

            if (PlayerData.getStaffBranchID().get(player.getName()) == 3) {
                adminTeam.addEntry(player.getName());
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 2) {
                modTeam.addEntry(player.getName());
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 1) {
                helperTeam.addEntry(player.getName());
            }
        }

        if(PlayerData.getDonorBranchID().get(player.getName()) == 1) {
            if (PlayerData.getStaffBranchID().get(player.getName()) == 3) {
                adminTeam.addEntry(player.getName());
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 2) {
                modTeam.addEntry(player.getName());
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 1) {
                helperTeam.addEntry(player.getName());
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 0) {
                donor1Team.addEntry(player.getName());
            }
        }

        if(PlayerData.getDonorBranchID().get(player.getName()) == 2) {
            if (PlayerData.getStaffBranchID().get(player.getName()) == 3) {
                adminTeam.addEntry(player.getName());
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 2) {
                modTeam.addEntry(player.getName());
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 1) {
                helperTeam.addEntry(player.getName());
            }
            if (PlayerData.getStaffBranchID().get(player.getName()) == 0) {
                donor2Team.addEntry(player.getName());
            }
        }
    }

    public void removeEntries(Player player){
        adminTeam.removeEntry(player.getName());
        modTeam.removeEntry(player.getName());
        helperTeam.removeEntry(player.getName());
        donor2Team.removeEntry(player.getName());
        donor1Team.removeEntry(player.getName());
        noneTeam.removeEntry(player.getName());
    }
}
