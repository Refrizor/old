package me.aziah.lobby;

import me.aziah.server.PlayerData;
import me.aziah.server.PlayerStates;
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
            PlayerStates.getLobby().add(player.getName());
        } else {
            PlayerStates.getLobby().remove(player.getName());
            player.getInventory().clear();
        }
    }

    public void deployItems(Player player) {
        Inventory inventory = player.getInventory();
        inventory.setItem(0, Itemdata.getGames());
        inventory.setItem(8, Itemdata.getCosmetics());
    }

    public static boolean inLobby(Player player) {
        return PlayerStates.getLobby().contains(player.getName());
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
