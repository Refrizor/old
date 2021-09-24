package me.aziah.minigames.pvp;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PVPEvents implements Listener {

    @EventHandler
    public void onDamaged(EntityDamageEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();

            if (PVP.getGracePeriod().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHungerDrop(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        if (PVP.isIngame(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (PVP.isIngame(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            if (PVP.isIngame(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (PVP.isIngame(player)) {
            Material material = event.getCurrentItem().getType();
            event.setCancelled(true);

            switch (material) {
                case STONE_SWORD:
                    event.setCancelled(false);
                case SHIELD:
                    event.setCancelled(false);
                case AIR:
                    event.setCancelled(false);

            }
        }
    }

    @EventHandler
    public void onOtherDamage(EntityDamageEvent event) {

        if(event.getCause() == EntityDamageEvent.DamageCause.FALL){
            event.setCancelled(true);
        }

        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();


            if (player.getHealth() - event.getDamage() <= 0D) {
                event.setCancelled(true);
                Location deathLocation = player.getLocation();
                player.getWorld().strikeLightningEffect(new Location(player.getWorld(), deathLocation.getX(), deathLocation.getY() + 2, deathLocation.getZ()));
                player.setGameMode(GameMode.SPECTATOR);

                switch (event.getEntity().getLastDamageCause().getCause()){
                    case SUFFOCATION:
                        PVP.alertIngame(ChatColor.YELLOW + player.getName() + ChatColor.RED + " was taken by the ruins...");
                        break;
                }
                PVP.remove(player, true);
                PVP.getDead().add(player.getUniqueId());

                new PVP().winCheck();
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();


            if (player.getHealth() - event.getDamage() <= 0D) {
                event.setCancelled(true);
                Location deathLocation = player.getLocation();
                player.getWorld().strikeLightningEffect(new Location(player.getWorld(), deathLocation.getX(), deathLocation.getY() + 2, deathLocation.getZ()));
                player.setGameMode(GameMode.SPECTATOR);

                PVP.alertIngame(ChatColor.YELLOW + player.getName() + ChatColor.RED + " was taken out by " + ChatColor.AQUA + event.getDamager().getName());

                PVP.remove(player, true);
                PVP.getDead().add(player.getUniqueId());

                new PVP().winCheck();
            }
        }
    }
}