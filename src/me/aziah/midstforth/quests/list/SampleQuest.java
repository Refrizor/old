package me.aziah.midstforth.quests;

import me.aziah.midstforth.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SampleQuest implements Listener {

    private static final ItemStack map = new ItemStack(Material.PAPER);

    public static ItemStack getMap() {
        ItemMeta itemMeta = map.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Map");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "Quest item!");
        itemMeta.setLore(lore);
        map.setItemMeta(itemMeta);
        return map;
    }

    public static void deploy(Player player) {
        NPC.greet3(player, "George", "Oh-hey! You aren't from around here are ya?", "Well anyway, if you want to help, north-west of here there's a village nearby.", "Deliver this to them, thank you!");

        NPC.giveQuest(player, QuestTypes.INTRO.getName(), QuestTypes.INTRO.getDescription(), 100L, true);

        player.getInventory().addItem(getMap());
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (NPC.isType(event, "George")) {
            if (Quest.hasActiveQuest(player, QuestTypes.INTRO.getName())) {
                event.setCancelled(true);

                player.getInventory().removeItem(map);
                NPC.greet(player, "George", "Oh-oh, thank you! Reymond village sent you, didn't they? This means so much to me. This map will help us scout for food supplies! Here, take some money!");

                Quest.completeQuest(player, QuestTypes.INTRO.getName(), null, 10);
            }
        }
    }
}