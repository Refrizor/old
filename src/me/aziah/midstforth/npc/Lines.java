package me.aziah.midstforth.npc;

import org.bukkit.Sound;

public class Lines {

    public enum Ralph{
        TEST("Test", Sound.ENTITY_VILLAGER_TRADE);

        private final String message;
        private final Sound sound;

        Ralph(String message, Sound sound){
            this.message = message;
            this.sound = sound;
        }

        public String getMessage() {
            return message;
        }

        public Sound getSound() {
            return sound;
        }
    }
}
