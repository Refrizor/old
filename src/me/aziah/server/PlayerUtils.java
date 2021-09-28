package me.aziah.server;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerUtils {

    public static boolean clickedMenuItem(InventoryClickEvent event, String name) {
        return event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(name);
    }

    public static void playSound(Player player, Sound sound, Float pitch) {
        player.playSound(player.getLocation(), sound, 10F, pitch);
    }

    public static void addPotion(Player player, PotionEffectType type, int duration, int amplifier, boolean particles, boolean icon) {
        PotionEffect potionEffect = new PotionEffect(type, duration, amplifier, true, particles, icon);
        player.addPotionEffect(potionEffect);
    }

    public static void removePotions(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public static void reset(Player player) {
        removePotions(player);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setAllowFlight(false);
        player.setGameMode(GameMode.ADVENTURE);
    }

    //int fadeIn, int stay, int fadeOut
    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 10, 70, 20);
    }

    public static void cinematicEffects(Player player, boolean active) {
        if (active) {
            player.stopSound("");
            player.setGameMode(GameMode.SPECTATOR);
            player.setGravity(false);
            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0);
        } else {
            player.setGameMode(GameMode.SURVIVAL);
            player.setGravity(true);
            player.setWalkSpeed(0.2F);
            player.setFlying(false);
            player.setFlySpeed(0.1F);
        }
    }

    public static void blind(Player player, int time){
        PlayerUtils.addPotion(player, PotionEffectType.BLINDNESS, time, 1, true, false);

    }
}
