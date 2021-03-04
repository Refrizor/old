package me.refriz.midstforth.npc;

import org.bukkit.entity.EntityType;

public enum NPCs {
    RALPH("Ralph", EntityType.VILLAGER);

    private final String name;
    private final EntityType type;

    NPCs(String name, EntityType type){
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
