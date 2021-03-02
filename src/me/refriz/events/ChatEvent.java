package me.refriz.events;

import me.refriz.server.PlayerData;
import me.refriz.ranks.Rank;
import me.refriz.server.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        int donorLevel = PlayerData.getBranchNumber(player, "donor");
        int staffLevel = PlayerData.getBranchNumber(player, "staff");
        int builderLevel = PlayerData.getBranchNumber(player, "builder");

        if (staffLevel == 3) {
            event.setFormat(Rank.ADMIN.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
        }

        if(donorLevel == 0 && staffLevel == 0){
            event.setFormat(Rank.NONE.getNameColor() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
        }

        if(donorLevel == 0){

            if (staffLevel == 1) {
                event.setFormat(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
            }
            if (staffLevel == 2) {
                event.setFormat(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
            }
        }

        if(donorLevel == 1){

            if(staffLevel == 0){
                event.setFormat(Rank.DONOR.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
            }
            if (staffLevel == 1) {
                event.setFormat(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
            }
            if (staffLevel == 2) {
                event.setFormat(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
            }
        }

        if(donorLevel == 2){

            if(staffLevel == 0){
                event.setFormat(Rank.DONOR2.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
            }
            if (staffLevel == 1) {
                event.setFormat(Rank.HELPER.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
            }
            if (staffLevel == 2) {
                event.setFormat(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());
            }
        }
    }
}


//TODO %2$s

//event.setFormat(Rank.MOD.getPrefix() + Messages.SPACER.getMessage() + player.getName() + Messages.SPACER_RESET.getMessage() + event.getMessage());