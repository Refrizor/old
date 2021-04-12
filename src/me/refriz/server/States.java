package me.refriz.server;

import me.refriz.Inferris;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class States {

    private static final List<String> lobby = new ArrayList<>();
    private static final List<String> vanish = new ArrayList<>();
    private static final List<String> apartment = new ArrayList<>();

    public static List<String> getApartment() {
        return apartment;
    }

    public static List<String> getLobby() {
        return lobby;
    }

    public static List<String> getVanish() {
        return vanish;
    }
}
