package me.refriz.server;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerUtils {

    public static boolean clickedMenuItem(InventoryClickEvent event, String name){
        return event.getCurrentItem().getItemMeta().getDisplayName().contains(name);
    }
}
