package me.aziah.minigames.pvp;

import me.aziah.Inferris;
import me.aziah.lobby.Lobby;
import me.aziah.server.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class PVPGame {

    private static final List<Player> ingame = new ArrayList<>();
    private static final List<String> queue = new ArrayList<>();
    private static final List<String> gracePeriod = new ArrayList<>();
    private static final List<String> spectate = new ArrayList<>();
    private static boolean started;
    BukkitScheduler scheduler = Bukkit.getScheduler();

    public void startGame() {
        started = true;

        Teams.sortPlayers(ingame);

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (isIngame(all)) {

                if(Teams.getTeamOfPlayer(all) == Teams.Team.BLUE){
                    PlayerUtils.sendTitle(all, ChatColor.BLUE + "Blue", "Prepare to fight...");
                }
                if(Teams.getTeamOfPlayer(all) == Teams.Team.RED){
                    all.sendMessage("Red");
                    PlayerUtils.sendTitle(all, ChatColor.RED + "Red", "Prepare to fight...");
                }

                this.startGracePeriod(all);
            }
        }
    }

    private int timer;
    private static int taskID;

    public void startPrematchTimer(Player player) {
        timer = 5;
        if (getQueue().size() >= 2) {
            messageToPlaying(ChatColor.YELLOW + "Starting in " + ChatColor.AQUA + timer, Sound.BLOCK_NOTE_BLOCK_PLING, true);
            taskID = scheduler.scheduleSyncRepeatingTask(Inferris.getInstance(), new Runnable() {
                @Override
                public void run() {
                    timer --;

                    if(getQueue().size() <=1){
                        scheduler.cancelTask(taskID);
                        messageToPlaying(ChatColor.RED + "Stopping countdown", Sound.BLOCK_NOTE_BLOCK_BANJO, true);
                    }
                    if(timer > 0) {
                        messageToPlaying(ChatColor.YELLOW + "Starting in " + ChatColor.AQUA + timer, Sound.BLOCK_NOTE_BLOCK_PLING, true);
                    }

                    if(timer == 0){
                        scheduler.cancelTask(taskID);
                        if(getQueue().size() >=1){
                            for(Player all : Bukkit.getOnlinePlayers()){
                                if(getQueue().contains(all.getName())){
                                    new Lobby().send(player, false);
                                    getIngame().add(all);
                                    getQueue().remove(all.getName());
                                }
                            }
                            startGame();
                        }
                    }
                }
            }, 20L, 20L);
        }
    }

    public void startGracePeriod(Player player){
        for(Player all : Bukkit.getOnlinePlayers()) {
            getGracePeriod().add(all.getName());
        }

        messageToPlaying(ChatColor.YELLOW + "Grace period has started! Get ready to fight.", Sound.ENTITY_ENDER_DRAGON_GROWL, true);

        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                messageToPlaying(ChatColor.YELLOW + "Grace period has ended!", Sound.ENTITY_ENDER_DRAGON_HURT, true);

                for(Player all : Bukkit.getOnlinePlayers()){
                    getGracePeriod().remove(all.getName());
                }
            }
        }, 160L);
    }

    public static void messageToPlaying(String message, Sound sound, boolean playSound){
        for(Player all : Bukkit.getOnlinePlayers()){
            if(ingame.contains(all.getName()) || queue.contains(all.getName())){
                all.sendMessage(message);
                if(playSound){
                    PlayerUtils.playSound(all, sound, 1F);
                }
            }
        }
    }

    public static List<Player> getIngame() {
        return ingame;
    }

    public static List<String> getQueue() {
        return queue;
    }

    public static boolean hasStarted() {
        return started;
    }

    public static List<String> getGracePeriod() {
        return gracePeriod;
    }

    public static List<String> getSpectate() {
        return spectate;
    }

    public static boolean isIngame(Player player){
        return getIngame().contains(player);
    }

    public static boolean isSpectating(Player player){
        return getSpectate().contains(player.getName());
    }

    public static boolean inQueue(Player player){
        return getQueue().contains(player.getName());
    }
}
