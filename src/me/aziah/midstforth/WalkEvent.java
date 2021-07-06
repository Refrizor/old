package me.aziah.midstforth;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class WalkEvent implements Listener {

    private static List<String> tripwire1 = new ArrayList<>();
    private static List<String> tripwire2 = new ArrayList<>();
    private static List<String> tripwire3 = new ArrayList<>();

    private static final List<String> temporaryDiscover = new ArrayList<>();


    @EventHandler
    public void onWalk(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location loc = event.getPlayer().getLocation().clone().subtract(0, 1, 0);
        Block b = loc.getBlock();

        if (!States.getDiscoveredLab().contains(player.getName())) {


            if (b.getType() == Material.CYAN_TERRACOTTA) {
                if (b.getX() == -84 && b.getY() == 72 && b.getZ() == -177) {
                    if (!getTripwire1().contains(player.getName())) {
                        getTripwire1().add(player.getName());

                        player.playSound(player.getLocation(), "signal_detected", 10F, 1F);
                        temporaryDiscover.add(player.getName());
                    }
                }

                if (b.getX() == -84 && b.getY() == 32 && b.getZ() == -218) {
                    if (!getTripwire2().contains(player.getName())) {
                        getTripwire2().add(player.getName());

                        player.playSound(player.getLocation(), "station_alarm", 10F, 1F);
                    }
                }
            }

            if (temporaryDiscover.contains(player.getName())) {
                if(b.getType() == Material.STONE_BRICKS) {
                    if (b.getX() == -101 && b.getY() == 32 && b.getZ() == -226) {
                        if (!getTripwire3().contains(player.getName())) {
                            player.playSound(player.getLocation(), "signal_detected", 10F, 1F);
                            getTripwire3().add(player.getName());
                        }
                    }
                }
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
}
