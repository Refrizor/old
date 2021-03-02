package me.refriz.midstforth.npc;

public enum NPCs {
    RALPH("Ralph");

    private final String name;

    NPCs(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
