package me.aziah.events;

import me.aziah.Inferris;
import me.aziah.midstforth.objectives.Objectives;
import me.aziah.server.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cinematics implements Listener {

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
        player.playSound(player.getLocation(), "theme", 10000F, 1F);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                PlayerUtils.sendTitle(player, ChatColor.RED + "2193 days", ChatColor.YELLOW + "since the worldwide outbreak");
                player.teleport(new Location(Bukkit.getWorld("world"), 533.527, 66.30956, -805.664, 165.9F, 16.1F));

                teleportPoint(player, new Location(Bukkit.getWorld("world"), -76, 33, -225, 97.2F, 2.0F), 80L, 10L, 100);

            }
        }, 120L);
    }

    public static void teleportPoint(Player player, Location location, long whenToBlind, long whenToTeleport, int blindDuration){
        BukkitScheduler scheduler = Bukkit.getScheduler();

        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {

                PlayerUtils.blind(player, blindDuration);

                scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        player.teleport(location);

                        inCinematic.remove(player.getUniqueId());
                    }
                }, whenToTeleport);
            }
        }, whenToBlind);
    }

    public static List<UUID> getInCinematic() {
        return inCinematic;
    }


    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (inCinematic.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
