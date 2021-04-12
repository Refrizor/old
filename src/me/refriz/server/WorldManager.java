package me.refriz.server;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorldManager {

    public static void load(WorldCreator name){
        try {
            Bukkit.getServer().createWorld(name);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void unload(String name){
        Bukkit.getServer().getWorld(name).save();
        Bukkit.getServer().unloadWorld(name, true);
    }

    public static void create(String name, WorldType type){
        WorldCreator worldCreator = new WorldCreator(name); //name of world
        worldCreator.type(type);
        worldCreator.createWorld();
    }

    public static void createWithSeed(String name, WorldType type, long seed){
        WorldCreator worldCreator = new WorldCreator(name); //name of world
        worldCreator.type(type);
        worldCreator.seed(seed);
        worldCreator.createWorld();
    }

    public static void deleteWorld(String worldName){
        try{
            unload(worldName);
            File file = Paths.get(worldName).toFile();
            FileVisitor.removeDirectory(file);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void duplicateWorld(String existingWorld, String newWorld){

        try{
            FileVisitor.copyDirectoryFileVisitor(existingWorld, newWorld);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void addWorldInstance(String from, String to, WorldType worldType){
        duplicateWorld(from, to);
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            player.sendMessage("Okay, loading:");
        }

        Path path = Paths.get(to + "/uid.dat");
        try {
            Files.delete(path);
        }catch(Exception e){
            e.printStackTrace();
        }

        WorldCreator worldCreator = new WorldCreator(to);
        worldCreator.type(worldType);
        load(worldCreator);
    }
}