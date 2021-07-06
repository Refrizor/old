package me.aziah.midstforth.quests;

import me.aziah.midstforth.npc.NPCTypes;

public enum QuestTypes {
    INTRO(NPCTypes.JOSEPH.getName(), "Delivery", "Fish some salmon for Joseph and the community!");

    private final String npc;
    private final String name;
    private final String description;

    QuestTypes(String npc, String name, String description){
        this.npc = npc;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}