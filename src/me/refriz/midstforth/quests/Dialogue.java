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

    public static void deploy(Player player, Object npc, Object quest) {

        if (!Quest.hasQuest(player, quest) && !(Quest.hasFinishedQuest(player, quest))) {
            sendDialogue1(player, npc, quest);


        }
    }

    public static void sendDialogue1(Player player, Object npc, Object quest) {
        if (npc == NPCs.RALPH.getName()) {
            player.sendMessage(Lines.Ralph.TEST.getMessage());
            Quest.addQuest(player, quest.toString());
        }
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event){
        if(clicked(event, NPCs.RALPH.getName(), NPCs.RALPH.getType())){
            event.setCancelled(true);
            Player player = event.getPlayer();

            deploy(player, NPCs.RALPH.getName(), Quests.TEST_1.getName());
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
}