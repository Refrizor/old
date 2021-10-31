package me.aziah.midstforth.npc;

import me.aziah.Inferris;
import me.aziah.midstforth.Midstforth;
import me.aziah.midstforth.States;
import me.aziah.midstforth.quests.Quest;
import me.aziah.midstforth.quests.QuestTypes;
import me.aziah.midstforth.quests.list.QuestIntro;
import me.aziah.midstforth.quests.list.QuestSatellite;
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

            if (NPC.isType(event, NPCTypes.JOSEPH.getName())) {
                new QuestIntro().onClick(event);
                event.setCancelled(true);
                if (!Quest.questOnRecord(player, QuestTypes.INTRO.getName())) {

                    new QuestIntro().deploy(player);
                }
            }

            if(NPC.isType(event, NPCTypes.KYLE.getName())){
                event.setCancelled(true);
                NPC.greetSad(player, NPCTypes.KYLE.getName(), "The world has changed so much since the incident...");
            }
            if(NPC.isType(event, NPCTypes.AMOS.getName())){
                event.setCancelled(true);
                if(!States.getDiscoveries().containsKey(player.getUniqueId()) && !States.getDiscoveries().containsValue("satellite1")){
                    if(!Quest.questOnRecord(player, QuestTypes.SATELLITE.getName())){

                        new QuestSatellite().deploy(player);
                    }
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
                    case "Kyle":
                    case "Amos":
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