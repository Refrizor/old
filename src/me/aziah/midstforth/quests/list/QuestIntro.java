package me.aziah.midstforth.quests.list;

import me.aziah.Inferris;
import me.aziah.midstforth.npc.NPC;
import me.aziah.midstforth.npc.NPCEvents;
import me.aziah.midstforth.quests.Quest;
import me.aziah.midstforth.quests.QuestTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class QuestIntro implements Listener {

    public static void deploy(Player player) {
        NPCEvents.getCanClick().add(player.getName());
        NPC.greet3(player, "Joseph", "Oh-hey! You aren't from around here are ya?", "Well anyway, if you want to help, go catch some fish!", "Maybe I'll give ya something in return!");
        NPC.giveQuest(player, QuestTypes.INTRO.getName(), QuestTypes.INTRO.getDescription(), 100L, true);

        player.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();


        if (NPC.isType(event, "Joseph")) {
            if (Quest.hasActiveQuest(player, QuestTypes.INTRO.getName())) {
                event.setCancelled(true);

                if (!NPCEvents.getCanClick().contains(player.getName())) {

                    if (player.getInventory().getItemInMainHand().getType() == Material.SALMON) {
                        if (player.getInventory().getItemInMainHand().getAmount() >= 3) {

                            NPCEvents.getCanClick().add(player.getName());

                            NPC.greet3(player, "Joseph", "Quite the fish catcher, huh? Thank you!", "This really means a lot to our community.", "Here is some money. You are always welcome here stranger!");
                            player.getInventory().removeItem(new ItemStack(Material.SALMON, 3));
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {

                                @Override
                                public void run() {
                                    Quest.completeQuest(player, QuestTypes.INTRO.getName(), null, 10);
                                }
                            }, 90L);

                        } else if (player.getInventory().getItemInMainHand().getAmount() < 3) {
                            NPC.greet(player, "Joseph", "Thanks, I need more though, sorry!");
                        }
                    }
                }
            }
        }
    }
}