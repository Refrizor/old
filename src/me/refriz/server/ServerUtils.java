package me.refriz.server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerUtils {

    public static void sendForAll(String message){
        for(Player all : Bukkit.getServer().getOnlinePlayers()){
            all.sendMessage(message);
        }
    }
}
