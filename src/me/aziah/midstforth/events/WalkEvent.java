package me.aziah.midstforth.events;

import me.aziah.midstforth.events.areas.Lab_A;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WalkEvent implements Listener {

    @EventHandler
    public void onWalk(PlayerMoveEvent event) {

        new Lab_A().onWalk(event);
    }
}
