package me.refriz.midstforth.quests;

import me.refriz.midstforth.npc.NPCs;
import org.bukkit.ChatColor;

public enum Quests {
    TEST_2("Test 2", null, NPCs.RALPH.getName(), 10),
    TEST_1("Discovery", ChatColor.GRAY + "Explore the foreign territory", NPCs.RALPH.getName(), 5);

    private final String name;
    private final String description;
    private final String npc;
    private final Integer reward;

    Quests(String name, String description, String npc, Integer reward){
        this.name = name;
        this.description = description;
        this.npc = npc;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getNpc() {
        return npc;
    }

    public Integer getReward() {
        return reward;
    }
}
