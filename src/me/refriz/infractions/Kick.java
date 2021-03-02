package me.refriz.infractions;

import org.bukkit.entity.Player;

public class Kick {

    public static void init(Player player, String reason){

        player.kickPlayer(reason);
    }
}
