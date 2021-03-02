package me.refriz.ranks;

import me.refriz.Inferris;
import me.refriz.server.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class Permission {

    public void deployPermissions(Player player){
        PermissionAttachment attachment = player.addAttachment(Inferris.getInstance());

        if(PlayerData.getStaffBranchID().get(player.getName()) == 1){
            attachment.setPermission("test1", true);
        }
    }
}
