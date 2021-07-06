package me.aziah.server;

import org.bukkit.Sound;

public enum Sounds {
    QUERY(Sound.BLOCK_NOTE_BLOCK_FLUTE, 10F),
    SUCCESS(Sound.BLOCK_NOTE_BLOCK_FLUTE, 0.8F),
    ERROR(Sound.BLOCK_NOTE_BLOCK_BANJO, 0.7F);

    private final Sound sound;
    private final Float pitch;

    Sounds(Sound sound, Float pitch){
        this.sound = sound;
        this.pitch = pitch;
    }

    public Sound getSound() {
        return sound;
    }

    public Float getPitch() {
        return pitch;
    }
}
