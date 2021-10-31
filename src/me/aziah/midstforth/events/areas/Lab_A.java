package me.aziah.midstforth.events.areas;

import me.aziah.Inferris;
import me.aziah.midstforth.Sounds;
import me.aziah.midstforth.States;
import me.aziah.server.PlayerUtils;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class Lab_A {

    private static final List<String> tripwire1 = new ArrayList<>();
    private static final List<String> tripwire2 = new ArrayList<>();
    private static final List<String> tripwire3 = new ArrayList<>();

    private static final List<String> temporaryDiscover = new ArrayList<>();

    public void onWalk(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = event.getPlayer().getLocation().clone().subtract(0, 1, 0);
        Block b = loc.getBlock();

        if (!States.getPowerLab().get(player.getUniqueId())) {

            /*
            Lab entrance
             */
            if (b.getType() == Material.CYAN_TERRACOTTA) {

                if (b.getX() == -84 && b.getY() == 72 && b.getZ() == -177) {
                    if (!getTripwire1().contains(player.getName())) {
                        getTripwire1().add(player.getName());

                        player.playSound(player.getLocation(), Sounds.SIGNAL_DETECTED.getName(), 10F, 1F);
                        temporaryDiscover.add(player.getName());
                    }
                }

                if (b.getX() == -84 && b.getY() == 32 && b.getZ() == -218) {
                    if (!getTripwire2().contains(player.getName())) {
                        getTripwire2().add(player.getName());

                        player.playSound(player.getLocation(), Sounds.ALARM.getName(), 10F, 1F);
                    }
                }
            }
        }

                    /*
            Went through unlock door gate
             */

        if (temporaryDiscover.contains(player.getName())) {
            if (b.getType() == Material.STONE_BRICKS) {
                if (b.getX() == -101 && b.getY() == 32 && b.getZ() == -226) {
                    if (!getTripwire3().contains(player.getName())) {
                        player.playSound(player.getLocation(), Sounds.SIGNAL_DETECTED.getName(), 10F, 1F);
                        getTripwire3().add(player.getName());

                        BukkitScheduler scheduler = Bukkit.getScheduler();
                        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                player.playSound(player.getLocation(), Sounds.LAB_EVENT1.getName(), 10F, 1F);
                                player.setWalkSpeed(0F);
                                PlayerUtils.addPotion(player, PotionEffectType.SLOW, 260, 1, false, true);

                                scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                                    @Override
                                    public void run() {
                                        player.setWalkSpeed(0.2F);
                                    }
                                }, 200L);
                            }
                        }, 60L);
                    }
                }
            }
        }




        /*
        Restores 'power' light when you come here in the future
         */
        if (States.getPowerLab().get(player.getUniqueId())) {
            if (b.getX() == -84 && b.getY() == 72 && b.getZ() == -177) {
                BlockData blockData = Bukkit.createBlockData(Material.REDSTONE_LAMP);
                ((Lightable) blockData).setLit(true);

                player.sendBlockChange(new Location(player.getWorld(), -68, 36, -248), blockData);
            }
        }
    }

    public static List<String> getTripwire1() {
        return tripwire1;
    }

    public static List<String> getTripwire2() {
        return tripwire2;
    }

    public static List<String> getTripwire3() {
        return tripwire3;
    }

    public static List<String> getTemporaryDiscover() {
        return temporaryDiscover;
    }
}
