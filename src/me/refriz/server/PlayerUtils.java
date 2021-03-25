package me.refriz.server;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerUtils {

    public static boolean clickedMenuItem(InventoryClickEvent event, String name){
        return event.getCurrentItem().getItemMeta().getDisplayName().contains(name);
    }

    public static void playSound(Player player, Sound sound, Float pitch){
        player.playSound(player.getLocation(), sound,10F, pitch);
    }
}
