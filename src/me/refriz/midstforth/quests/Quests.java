package me.refriz.midstforth.quests;

import me.refriz.midstforth.npc.NPCs;

public enum Quests {
    TEST_2("Test 2", NPCs.RALPH.getName(), 10),
    TEST_1("Discovery", NPCs.RALPH.getName(), 5);

    private final String name;
    private final String npc;
    private final Integer reward;

    Quests(String name, String npc, Integer reward){
        this.name = name;
        this.npc = npc;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public Integer getReward() {
        return reward;
    }
}
