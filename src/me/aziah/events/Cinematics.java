package me.aziah.events;

import me.aziah.Inferris;
import me.aziah.midstforth.objectives.Objectives;
import me.aziah.server.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Cinematics {

    public void introduction(Player player){
        player.getInventory().clear();
        player.setHealth(20D);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.stopSound("");
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.BLINDNESS, 90, 10, false, false);
        player.addPotionEffect(potionEffect);
        player.teleport(new Location(player.getWorld(), -1473, 71, 1730, -124, 1));

        PlayerUtils.sendTitle(player, ChatColor.AQUA + "The Beyond...", ChatColor.YELLOW + "An epic roleplay storyline");
        PlayerUtils.playSound(player, Sound.BLOCK_BELL_USE, 0.9F);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                PlayerUtils.sendTitle(player, ChatColor.RED + "2193 days", ChatColor.YELLOW + "since the worldwide outbreak");

                scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Objectives.addObjective(player, "intro", "Go to the village", "See if there is anything you can do to help out the local village");
                    }
                }, 100L);
            }
        }, 120L);
    }
}
