package me.refriz.server;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerUtils {

    public static boolean clickedMenuItem(InventoryClickEvent event, String name){
        return event.getCurrentItem().getItemMeta().getDisplayName().contains(name);
    }

    public static void playSound(Player player, Sound sound, Float pitch){
        player.playSound(player.getLocation(), sound,10F, pitch);
    }

    public static void addPotion(Player player, PotionEffectType type, int duration, int amplifier, boolean hidden, boolean icon) {
        if (hidden) {
            if (icon) {
                PotionEffect potionEffect = new PotionEffect(type, duration, amplifier, true, false, true);
                player.addPotionEffect(potionEffect);
            }else{
                PotionEffect potionEffect = new PotionEffect(type, duration, amplifier, true, false, false);
                player.addPotionEffect(potionEffect);
            }
        }
    }

    public static void removePotions(Player player){
        for(PotionEffect potionEffect : player.getActivePotionEffects()){
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public static void reset(Player player){
        removePotions(player);
        player.setHealth(10);
        player.setFoodLevel(20);
    }

    //int fadeIn, int stay, int fadeOut
    public static void sendTitle(Player player, String title, String subtitle, boolean slow) {
        if (slow) {
            player.sendTitle(title, subtitle, 10, 50, 20);
        }else{
            player.sendTitle(title, subtitle, 10, 30, 10);

        }
    }
}
