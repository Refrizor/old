package me.aziah.midstforth.events;

import me.aziah.events.Cinematics;
import me.aziah.midstforth.Midstforth;
import me.aziah.midstforth.States;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
public class LookEvent implements Listener {

    @EventHandler
    public void onLook(PlayerMoveEvent event) {

        if (!Cinematics.getInCinematic().contains(event.getPlayer().getUniqueId())) {

            // [ K : V]
            // UUID, "satellite1"
            if (!States.getSatellite1().contains(event.getPlayer().getUniqueId())) {
                Player player = event.getPlayer();

                if (player.getTargetBlock(null, 200).getX() == 531 && player.getTargetBlock(null, 200).getY() == 65 && player.getTargetBlock(null, 200).getZ() == -813) {
                    States.getSatellite1().add(player.getUniqueId());
                    States.getDiscoveries().put(player.getUniqueId(), "satellite1");

                    Midstforth.discoverAdd(player, "satellite1");

                    player.playSound(player.getLocation(), "mystery3", 10F, 1F);
                }
            }
        }

        new Cinematics().onMove(event);
    }
}