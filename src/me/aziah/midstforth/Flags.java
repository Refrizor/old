package me.aziah.midstforth;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public enum Flags {
    APARTMENT(ChatColor.YELLOW + "Apartment", Types.getApartment()),
    SHIP(ChatColor.AQUA + "Ship", Types.getShip()),
    OTHER(null, Types.getOther());

    private final String name;
    private final List<String> list;

    Flags(String name, List<String> list){
        this.name = name;
        this.list = list;
    }

    public static class Types{
        private static final List<String> apartment = new ArrayList<>();
        private static final List<String> ship = new ArrayList<>();
        private static final List<String> other = new ArrayList<>();

        public static List<String> getApartment() {
            return apartment;
        }

        public static List<String> getShip() {
            return ship;
        }

        public static List<String> getOther() {
            return other;
        }
    }
}
