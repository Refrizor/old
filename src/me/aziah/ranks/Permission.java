package me.aziah.ranks;

import me.aziah.Inferris;
import me.aziah.server.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class Permission {

    public void deployPermissions(Player player){
        PermissionAttachment attachment = player.addAttachment(Inferris.getInstance());

        if(PlayerData.getStaffBranchID(player) < 3){
            attachment.setPermission("minecraft.command.msg", false);
        }

        if(PlayerData.getStaffBranchID(player) >=2){
            attachment.setPermission("minecraft.command.teleport", true);
        }

        if(PlayerData.getStaffBranchID(player) >=1) {
            attachment.setPermission("test1", true);
        }

        //If not staff:

        if(PlayerData.getStaffBranchID(player) < 1){
            attachment.setPermission("minecraft.command.me", false);
        }

        if(PlayerData.getDonorBranchID(player) >=1){
        }
    }
}
