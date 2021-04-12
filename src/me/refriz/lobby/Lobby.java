package me.refriz.lobby;

import me.refriz.server.PlayerData;
import me.refriz.server.States;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;

public class Lobby implements Listener {

    public void send(Player player, boolean toLobby) {
        if (toLobby) {
            this.deployItems(player);
            States.getLobby().add(player.getName());
        } else {
            States.getLobby().remove(player.getName());
            player.getInventory().clear();
        }
    }

    public void deployItems(Player player) {
        Inventory inventory = player.getInventory();
        inventory.setItem(0, Itemdata.getGames());
        inventory.setItem(8, Itemdata.getCosmetics());
    }

    public static boolean inLobby(Player player) {
        return States.getLobby().contains(player.getName());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (PlayerData.getStaffBranchID(event.getPlayer()) < 3) {
            if (inLobby(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (PlayerData.getStaffBranchID(event.getPlayer()) < 3) {
            if (inLobby(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (inLobby(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if(event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            if (inLobby(player)) {
                event.setCancelled(true);
            }
        }
    }
}
