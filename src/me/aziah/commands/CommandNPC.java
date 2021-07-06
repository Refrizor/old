package me.aziah.commands;

import me.aziah.midstforth.npc.NPC;
import me.aziah.server.Messages;
import me.aziah.server.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CommandNPC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if(PlayerData.getStaffBranchID(player) >=3){
            NPC.add(player, EntityType.VILLAGER, args[0]);
        }else{
            player.sendMessage(Messages.NO_PERM.getMessage());
        }
        return true;
    }
}
