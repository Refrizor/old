package me.aziah.minigames.pvp;

import me.aziah.Inferris;
import me.aziah.server.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import static me.aziah.minigames.pvp.PVP.getGracePeriod;
import static me.aziah.minigames.pvp.PVP.isIngame;

public class Graceperiod{

    private static int countdown = 5;
    private static int taskID;

    public static void init(){

        for(Player all : Bukkit.getServer().getOnlinePlayers()){
            if(isIngame(all)){
                getGracePeriod().add(all.getUniqueId());
            }
        }

        BukkitScheduler scheduler = Bukkit.getScheduler();
        taskID = scheduler.scheduleSyncRepeatingTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(getGracePeriod().contains(all.getUniqueId())){
                        all.sendMessage(ChatColor.AQUA + "Grace period ends in " + ChatColor.YELLOW + countdown);
                    }
                }
                countdown --;

                if(countdown == 0){
                    scheduler.cancelTask(taskID);

                    for(Player all : Bukkit.getOnlinePlayers()){
                        if(getGracePeriod().contains(all.getUniqueId())){
                            all.sendMessage(Messages.GRACE_END.getMessage());
                            PlayerUtils.playSound(all, Sound.ENTITY_ENDER_DRAGON_GROWL, 10F);
                            PVP.getGracePeriod().remove(all.getUniqueId());
                        }
                    }
                }
            }
        }, 60L, 20L);
    }
}
