package me.refriz.minigames.pvp;

import me.refriz.server.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffectType;

public class PVPEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(PVPGame.isIngame(event.getPlayer())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if(PVPGame.isIngame(event.getPlayer())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnyDamage(EntityDamageEvent event){
        if(event.getEntity().getType() == EntityType.PLAYER){
            Player player = (Player) event.getEntity();
            if(PVPGame.isSpectating(player)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getDamager().getType() == EntityType.PLAYER){
            Player player = (Player) event.getDamager();
            if(PVPGame.getGracePeriod().contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            if(PVPGame.isIngame(player)) {

                Location location = player.getLocation();
                if (event.getDamage() >= player.getHealth()) {


                    player.getWorld().strikeLightningEffect(player.getLocation().add(0, 1, 0));
                    PVPGame.messageToPlaying(ChatColor.RED + "" + player + " was killed by " + event.getDamager().getType(), null, false);

                    PVPGame.getSpectate().add(player.getName());
                    PVPGame.getIngame().remove(player);

                    player.teleport(location.add(0, 1, 0));
                    player.setAllowFlight(true);
                    player.setHealth(20.0);
                    PlayerUtils.addPotion(player, PotionEffectType.INVISIBILITY, 1000000, 1, true, false);
                }
            }
        }
    }

    @EventHandler
    public void onHungerDrop(FoodLevelChangeEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            if (PVPGame.isIngame(player) || PVPGame.inQueue(player) || PVPGame.isSpectating(player)){
                event.setCancelled(true);
            }
        }
    }
}
