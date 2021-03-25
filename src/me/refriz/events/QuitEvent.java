package me.refriz.events;

import me.refriz.ranks.Rank;
import me.refriz.server.PlayerData;
import me.refriz.server.States;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        States.getLobby().remove(player.getName());

        Rank.ADMIN.getRank().remove(player);
        Rank.MOD.getRank().remove(player);
        Rank.HELPER.getRank().remove(player);
        Rank.DONOR.getRank().remove(player);
        Rank.NONE.getRank().remove(player);

        States.getVanish().remove(player.getName());

        PlayerData.getMuted().remove(player.getName());
    }
}
