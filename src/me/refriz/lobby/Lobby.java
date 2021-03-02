package me.refriz.lobby;

import me.refriz.server.States;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Lobby implements Listener {

    public void deployItems(Player player) {

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            if (States.getLobby().contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }
}