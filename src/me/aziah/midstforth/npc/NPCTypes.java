package me.aziah.midstforth.npc;

import org.bukkit.entity.EntityType;

public enum NPCTypes {
    JOSEPH("Joseph", EntityType.VILLAGER);

    private final String name;
    private final EntityType type;

    NPCTypes(String name, EntityType type){
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public EntityType getType() {
        return type;
    }
}
