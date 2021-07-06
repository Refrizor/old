package me.aziah.midstforth;

import me.aziah.Inferris;
import me.aziah.server.Messages;
import me.aziah.server.PlayerStates;
import me.aziah.server.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffectType;

public class Regions extends States implements Listener {

    public static void enterApartment(Player player, boolean alert){
        getApartment().add(player.getName());
        PlayerUtils.addPotion(player, PotionEffectType.SLOW, 10000, 1, true, false);

        for(Player all : Bukkit.getServer().getOnlinePlayers()){
            player.hidePlayer(Inferris.getInstance(), all);
        }

        if(alert){
            player.sendMessage(Messages.ENTER_APARTMENT.getMessage());
        }
    }

    public static void exitApartment(Player player, boolean alert){
        getApartment().remove(player.getName());
        PlayerUtils.removePotions(player);

        for(Player all : Bukkit.getServer().getOnlinePlayers()) {
            if (!PlayerStates.getVanish().contains(all.getName())) {
                player.showPlayer(Inferris.getInstance(), all);
            }
        }
        if(alert){
            player.sendMessage(Messages.EXIT_APARTMENT.getMessage());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(getApartment().contains(player.getName())){
            event.setBuild(false);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(getApartment().contains(player.getName())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if(getApartment().contains(player.getName())){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event){
        Player player = event.getPlayer();
        if(getApartment().contains(player.getName())){
            event.setCancelled(true);
        }
    }
}
