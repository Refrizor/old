package me.aziah.events;

import me.aziah.Inferris;
import me.aziah.midstforth.objectives.Objectives;
import me.aziah.server.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cinematics {

    private BukkitScheduler scheduler = Bukkit.getScheduler();
    private int id;
    private static final List<UUID> inCinematic = new ArrayList<>();

    public void introduction(Player player) {
        inCinematic.add(player.getUniqueId());
        PlayerUtils.cinematicEffects(player, true);
        PlayerUtils.addPotion(player, PotionEffectType.BLINDNESS, 140, 1, true, false);
        PlayerUtils.sendTitle(player, ChatColor.AQUA + "The Beyond...", ChatColor.YELLOW + "An epic roleplay storyline");
        player.setPlayerTime(18000, true);
        player.setPlayerWeather(WeatherType.CLEAR);
        player.getInventory().clear();

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                PlayerUtils.sendTitle(player, ChatColor.RED + "2193 days", ChatColor.YELLOW + "since the worldwide outbreak");
                player.teleport(new Location(Bukkit.getWorld("world"), 533.527, 66.30956, -805.664, 165.9F, 16.1F));

                teleportPoint(player, new Location(Bukkit.getWorld("world"), -76, 33, -225), 70L);

            }
        }, 120L);
    }

    public void teleportPoint(Player player, Location location, long time){
        id = scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.teleport(location);
                scheduler.cancelTask(id);
            }
        }, time);
    }

    public static List<UUID> getInCinematic() {
        return inCinematic;
    }
}
