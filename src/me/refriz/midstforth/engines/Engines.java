package me.refriz.midstforth.engines;

import me.refriz.server.DatabaseConnector;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public enum Engines {
    ENGINE_3(ChatColor.AQUA + "Engine 3", 1000),
    ENGINE_2(ChatColor.AQUA + "Engine 2", 500),
    ENGINE_1(ChatColor.AQUA + "Engine 1", 0);

    private final String name;
    private final int cost;

    Engines(String name, int cost){
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public static class Actions{
        public static void purchase(Player player){

        }
    }
}
