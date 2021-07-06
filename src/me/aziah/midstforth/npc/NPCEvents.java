package me.aziah.midstforth.npc;

import me.aziah.Inferris;
import me.aziah.midstforth.quests.Quest;
import me.aziah.midstforth.quests.QuestTypes;
import me.aziah.midstforth.quests.list.QuestIntro;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class NPCEvents implements Listener {

    private static final List<String> canClick = new ArrayList<>();
    public static boolean damagable = true;

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        BukkitScheduler scheduler = Bukkit.getScheduler();

        if (!canClick.contains(player.getName())) {

            if (NPC.isType(event, "Joseph")) {
                if (!Quest.hasQuest(player, QuestTypes.INTRO.getName()) && !Quest.hasCompletedQuest(player, QuestTypes.INTRO.getName())) {
                    event.setCancelled(true);

                    QuestIntro.deploy(player);
                }
            }

            scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                @Override
                public void run() {
                    canClick.remove(player.getName());
                }
            }, 100L);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (damagable) {
            if (event.getEntity().getType() == EntityType.VILLAGER) {
                switch (event.getEntity().getName()) {
                    case "Joseph":
                    case "George":
                        event.setCancelled(true);
                        break;
                }
            }
        }
    }

    public static List<String> getCanClick() {
        return canClick;
    }
}