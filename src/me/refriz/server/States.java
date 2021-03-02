package me.refriz.server;

import java.util.ArrayList;
import java.util.List;

public class States {

    private static final List<String> lobby = new ArrayList<>();
    private static final List<String> vanish = new ArrayList<>();

    public static List<String> getLobby() {
        return lobby;
    }

    public static List<String> getVanish() {
        return vanish;
    }
}
