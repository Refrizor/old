package me.aziah.midstforth.quests.list;

import me.aziah.midstforth.npc.NPC;
import me.aziah.midstforth.npc.NPCEvents;
import me.aziah.midstforth.npc.NPCTypes;
import me.aziah.midstforth.quests.QuestTypes;
import org.bukkit.entity.Player;

public class QuestSatellite implements QuestBasic{

    @Override
    public void deploy(Player player) {
        NPCEvents.getCanClick().add(player.getName());
        NPC.greet2(player, NPCTypes.AMOS.getName(), "Hear there's some crash site nearby.", "They say it came from space, might be worth a look.");
        NPC.giveQuest(player, QuestTypes.SATELLITE.getName(), QuestTypes.SATELLITE.getDescription(), 100L, false);
    }
}
