package me.refriz.midstforth;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpacesuitItemdata {

    private static final ItemStack helmet = new ItemStack(Material.CARVED_PUMPKIN);
    private static final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    private static final ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
    private static final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

    public static ItemStack getHelmet() {
        ItemMeta itemMeta = helmet.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Astronaut Helmet");
        helmet.setItemMeta(itemMeta);
        return helmet;
    }

    public static ItemStack getChestplate() {
        ItemMeta itemMeta = chestplate.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Astronaut Suit");
        chestplate.setItemMeta(itemMeta);
        return chestplate;
    }

    public static ItemStack getLeggings() {
        ItemMeta itemMeta = leggings.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Astronaut Suit");
        leggings.setItemMeta(itemMeta);
        return leggings;
    }

    public static ItemStack getBoots() {
        ItemMeta itemMeta = boots.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Astronaut Suit");
        boots.setItemMeta(itemMeta);
        return boots;
    }
}
