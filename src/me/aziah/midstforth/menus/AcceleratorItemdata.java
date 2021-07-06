package me.aziah.midstforth.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AcceleratorItemdata {

    private static final ItemStack enable = new ItemStack(Material.CLOCK);
    private static final ItemStack disable = new ItemStack(Material.REDSTONE);

    public static ItemStack getEnable() {
        ItemMeta itemMeta = enable.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "Enable accelerator");
        enable.setItemMeta(itemMeta);
        return enable;
    }

    public static ItemStack getDisable() {
        ItemMeta itemMeta = disable.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Disable accelerator");
        disable.setItemMeta(itemMeta);
        return disable;
    }
}
