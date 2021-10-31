package me.aziah.ranks;

import me.aziah.server.Messages;
import me.aziah.server.PlayerData;
import org.bukkit.entity.Player;

import static me.aziah.Inferris.*;

public class Tab {

    public void deploy(Player player) {

        int donorLevel = PlayerData.getDonorBranchID(player);
        int staffLevel = PlayerData.getStaffBranchID(player);
        int builderLevel = PlayerData.getBuilderBranchID(player);
        int affiliateLevel = PlayerData.getAffiliateBranchID(player);


        switch (staffLevel) {
            case 3:
                adminTeam.addEntry(player.getName());
                break;
            case 2:
                modTeam.addEntry(player.getName());
                break;
            case 1:
                helperTeam.addEntry(player.getName());
                break;
        }

        switch (donorLevel) {
            case 2:
                if (staffLevel == 0) {
                    donor2Team.addEntry(player.getName());
                }
                break;
            case 1:
                if (staffLevel == 0) {
                    donor1Team.addEntry(player.getName());
                }
            case 0:
                if (staffLevel == 0) {
                    noneTeam.addEntry(player.getName());
                    break;
                }
        }

        switch (affiliateLevel) {
            case 1:
                if (staffLevel == 0) {
                    affiliate.addEntry(player.getName());
                    break;
                }
        }
    }

    public void removeEntries(Player player) {
        adminTeam.removeEntry(player.getName());
        modTeam.removeEntry(player.getName());
        helperTeam.removeEntry(player.getName());
        donor2Team.removeEntry(player.getName());
        donor1Team.removeEntry(player.getName());
        affiliate.removeEntry(player.getName());
        noneTeam.removeEntry(player.getName());
    }
}
