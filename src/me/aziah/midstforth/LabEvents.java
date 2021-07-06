package me.aziah.midstforth;

import me.aziah.Inferris;
import me.aziah.server.PlayerUtils;
import me.aziah.server.SQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Powerable;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftPowerable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;


public class LabEvents implements Listener {


    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();


            if (block.getX() == -100 && block.getY() == 34 || block.getY() == 33 && block.getZ() == -226) {
                if (!States.getPowerLab().contains(player.getName())) {
                    event.setCancelled(true);
                    PlayerUtils.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP, 1F);
                }
            }

            if (block.getType() == Material.LEVER) {
                if (!States.getPowerLab().contains(player.getName())) {
                    if (block.getX() == -67 && block.getY() == 34 && block.getZ() == -248) {
                        States.getPowerLab().add(player.getName());
                        SQLHandler.action("UPDATE `progression_states` SET `lab_power`='1' WHERE `uuid` = '" + player.getUniqueId() + "'");

                        player.playSound(player.getLocation(), "backup_power", 10F, 1F);

                        BlockData blockData = Bukkit.createBlockData(Material.REDSTONE_LAMP);
                        ((Lightable) blockData).setLit(true);

                        player.sendBlockChange(new Location(player.getWorld(), -68, 36, -248), blockData);
                    }
                }
            }
        }
    }
}