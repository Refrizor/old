package me.aziah.minigames.pvp;

import me.aziah.Inferris;
import me.aziah.server.PlayerUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class PVP {

    static int countdown = 5;

    public static final List<UUID> queue = new ArrayList<>();
    private final static List<UUID> gracePeriod = new ArrayList<>();
    private final static List<Player> ingame = new ArrayList<>();
    private final static List<UUID> dead = new ArrayList<>();
    private final static List<UUID> blueTeam = new ArrayList<>();
    private final static List<UUID> redTeam = new ArrayList<>();
    private static boolean hasStarted = false;

    public static List<UUID> getQueue() {
        return queue;
    }

    public static List<UUID> getGracePeriod() {
        return gracePeriod;
    }

    public static List<Player> getIngame() {
        return ingame;
    }

    public static boolean isIngame(Player player) {
        return getIngame().contains(player);
    }

    public static List<UUID> getDead() {
        return dead;
    }

    public static boolean isDead(UUID dead) {
        return getDead().contains(dead);
    }

    public static int getCountdown() {
        return countdown;
    }

    public static void alertQueue(String message) {
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            if (getQueue().contains(all.getUniqueId())) {
                all.sendMessage(message);
            }
        }
    }

    public static void alertIngame(String message) {
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            if (getIngame().contains(all)) {
                all.sendMessage(message);
            }
        }
    }

    static int taskID;

    public static void startCountdown() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (getQueue().size() >= 2) {
                    if (countdown >= 2) {
                        alertQueue(ChatColor.YELLOW + "Starting in " + ChatColor.AQUA + getCountdown() + ChatColor.YELLOW + " seconds");
                        countdown--;

                        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                            PlayerUtils.playSound(all, Sound.BLOCK_NOTE_BLOCK_PLING, 10F);
                        }

                    } else if (countdown == 1) {
                        alertQueue(ChatColor.YELLOW + "Starting in " + ChatColor.AQUA + getCountdown() + ChatColor.YELLOW + " second");
                        countdown--;

                        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                            PlayerUtils.playSound(all, Sound.BLOCK_NOTE_BLOCK_PLING, 10F);
                        }
                    }

                    if (countdown == 0) {
                        scheduler.cancelTask(taskID);
                        taskID = scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                startGame();
                                scheduler.cancelTask(taskID);
                            }
                        }, 20L);
                    }
                } else {
                    scheduler.cancelTask(taskID);
                    for (Player all : Bukkit.getServer().getOnlinePlayers()) {
                        PlayerUtils.playSound(all, Sound.BLOCK_NOTE_BLOCK_GUITAR, 10F);
                        all.sendMessage(Messages.STARTUP_CANCELLED.getMessage());
                        countdown = 10;
                    }
                }
            }
        }, 0L, 20L);
    }

    public static void startGame() {
        hasStarted = true;
        for (Player all : Bukkit.getServer().getOnlinePlayers()) {
            if (getQueue().contains(all.getUniqueId())) {
                getIngame().add(all);
                getQueue().remove(all.getUniqueId());
            }
            if (isIngame(all)) {
                PlayerUtils.reset(all);
                all.getInventory().clear();
                giveItems(all);
            }
        }

        Teams.sortPlayers(ingame);

        for(Player ingame : Bukkit.getServer().getOnlinePlayers()) {
            if (Teams.getTeamOfPlayer(ingame) == Teams.Team.BLUE) {
                ingame.sendMessage(Messages.BLUE_START.getMessage());
            }
            if (Teams.getTeamOfPlayer(ingame) == Teams.Team.RED) {
                ingame.sendMessage(Messages.RED_START.getMessage());
            }
        }

        Graceperiod.init();
    }

    private static void giveItems(Player player) {
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.GOLDEN_BOOTS));

        Inventory inventory = player.getInventory();
        inventory.setItem(0, new ItemStack(Material.STONE_SWORD));
        inventory.setItem(1, new ItemStack(Material.SHIELD));
    }

    public static void remove(Player player, boolean death) {
        getQueue().remove(player.getUniqueId());
        getIngame().remove(player);
        getGracePeriod().remove(player.getUniqueId());

        if (death) {
            Teams.removePlayerFromTeam(player);
        }
    }

    public static void setBorderSize(World world, double amount) {
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(amount);
    }

    public void winCheck() {
        if (Teams.getPlayersInTeam(Teams.Team.BLUE).size() == 0) {

        }
        if (Teams.getPlayersInTeam(Teams.Team.RED).size() == 0) {

        }
    }
}