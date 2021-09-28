package me.aziah.midstforth;

import me.aziah.Inferris;
import me.aziah.server.Messages;
import me.aziah.server.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class Cinematic {

    private BukkitScheduler scheduler = Bukkit.getScheduler();
    private int id;

    public void intro(Player player) {
        player.teleport(new Location(Bukkit.getWorld("world"), 1.513, 4, 1.366));
        PlayerUtils.addPotion(player, PotionEffectType.BLINDNESS, 99999, 1, true, false);
        PlayerUtils.addPotion(player, PotionEffectType.SLOW, 99999, 100, true, false);
        PlayerUtils.sendTitle(player, ChatColor.AQUA + "MIDSTFORTH", ChatColor.WHITE + "A Space RPG...");
        //PlayerUtils.playSound(player, Sound.MUSIC_CREDITS, 0.6F);

        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                PlayerUtils.reset(player);
                Regions.enterApartment(player, false);
                player.sendMessage(Messages.NEW_APARTMENT.getMessage());

            }
        }, 90L);
    }
}
