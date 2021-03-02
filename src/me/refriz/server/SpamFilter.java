package me.refriz.server;

import me.refriz.Inferris;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class SpamFilter implements Listener {

    int id;
    private static final List<String> canChat = new ArrayList<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!player.isOp()) {

            if (!getCanChat().contains(player.getName())) {

                getCanChat().add(player.getName());

                BukkitScheduler scheduler = Bukkit.getScheduler();
                id = scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        canChat.remove(player.getName());
                    }
                }, 40L);
            } else {
                player.sendMessage(ChatColor.RED + "Please wait until sending your next message.");
                event.setCancelled(true);
            }
        }
    }

    public static List<String> getCanChat() {
        return canChat;
    }
}
