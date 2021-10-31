package me.aziah.events;

import me.aziah.server.PlayerData;
import me.aziah.ranks.Rank;
import me.aziah.server.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        int donorLevel = PlayerData.getDonorBranchID(player);
        int staffLevel = PlayerData.getStaffBranchID(player);
        int builderLevel = PlayerData.getBuilderBranchID(player);
        int affiliateLevel = PlayerData.getAffiliateBranchID(player);

        String locationPrefix = ChatColor.GRAY + "(" + PlayerData.getLocation().get(player.getUniqueId()) + ") ";

        if (!PlayerData.getMuted().contains(player.getName())) {

            switch(staffLevel){
                case 3:
                    event.setFormat(locationPrefix + Rank.ADMIN.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
                    break;
                case 2:
                    event.setFormat(locationPrefix + Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
                    break;
                case 1:
                    event.setFormat(locationPrefix + Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
                    break;
            }

            switch (donorLevel) {
                case 2:
                    if (staffLevel == 0) {
                        event.setFormat(locationPrefix + Rank.DONOR2.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
                    }
                    break;
                case 1:
                    if (staffLevel == 0) {
                        event.setFormat(locationPrefix + Rank.DONOR.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
                    }
                case 0:
                    if (staffLevel == 0) {
                        event.setFormat(locationPrefix + Rank.NONE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
                        break;
                    }
            }

            switch (affiliateLevel) {
                case 1:
                    if (staffLevel == 0) {
                        event.setFormat(locationPrefix + Rank.AFFILIATE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
                        break;
                    }
            }

            /* Admin */
//            if (staffLevel == 3) {
//                event.setFormat(locationPrefix + Rank.ADMIN.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//            }
//
//            if (donorLevel == 0 && staffLevel == 0) {
//                event.setFormat(locationPrefix + Rank.NONE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//            }
//
//            if (donorLevel == 0) {
//
//                if (staffLevel == 1) {
//                    event.setFormat(locationPrefix + Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//                }
//                if (staffLevel == 2) {
//                    event.setFormat(locationPrefix + Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//                }
//            }
//
//            if (donorLevel == 1) {
//
//                if (staffLevel == 0) {
//                    event.setFormat(locationPrefix + Rank.DONOR.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//                }
//                if (staffLevel == 1) {
//                    event.setFormat(locationPrefix + Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//                }
//                if (staffLevel == 2) {
//                    event.setFormat(locationPrefix + Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//                }
//            }
//
//            if (donorLevel == 2) {
//
//                if (staffLevel == 0) {
//                    event.setFormat(locationPrefix + Rank.DONOR2.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//                }
//                if (staffLevel == 1) {
//                    event.setFormat(locationPrefix + Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//                }
//                if (staffLevel == 2) {
//                    event.setFormat(locationPrefix + Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//                }
//            }
//
//            if(affiliateLevel == 1){
//                event.setFormat(locationPrefix + Rank.AFFILIATE.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
//            }
        } else {
            player.sendMessage(ChatColor.RED + "You are muted!");
            event.setCancelled(true);
        }
    }
}


//TODO %2$s

//event.setFormat(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());