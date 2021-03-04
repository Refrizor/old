package me.refriz.midstforth.quests;

import me.refriz.midstforth.npc.Lines;
import me.refriz.midstforth.npc.NPCs;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Dialogue implements Listener {

    public static void deploy(Player player, Object npc, Object quest, String line) {

        if (!Quest.hasQuest(player, quest)) {

            player.sendMessage(line);
            Quest.addQuest(player, quest.toString());
        }
    }

    public static boolean clicked(PlayerInteractAtEntityEvent event, Object NPC, EntityType type) {
        if(event.getRightClicked().getType() == type){
            if(event.getRightClicked().getCustomName().equals(NPC)){
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event){
        if(clicked(event, NPCs.RALPH.getName(), NPCs.RALPH.getType())){
            event.setCancelled(true);
            Player player = event.getPlayer();

            deploy(player, NPCs.RALPH.getName(), Quests.TEST_1.getName(), Lines.Ralph.TEST.getMessage());
        }
    }
}