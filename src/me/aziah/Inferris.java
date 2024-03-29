package me.aziah;

import me.aziah.commands.*;
import me.aziah.minigames.pvp.CommandPVP;
import me.aziah.server.Initializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Inferris extends JavaPlugin {

    public static Inferris instance;

    public static Scoreboard mainScoreboard;
    public static Team adminTeam;
    public static Team modTeam;
    public static Team helperTeam;
    public static Team donor2Team;
    public static Team donor1Team;
    public static Team affiliate;
    public static Team noneTeam;

    @Override
    public void onEnable(){
        instance = this;
        this.getLogger().info("Loaded!");
        getCommand("connector").setExecutor(new CommandConnector());
        getCommand("rankchecker").setExecutor(new CommandRankchecker());
        getCommand("setrank").setExecutor(new CommandSetrank());
        getCommand("admin").setExecutor(new CommandAdmin());
        getCommand("vanish").setExecutor(new CommandVanish());
        getCommand("fly").setExecutor(new CommandFly());
        getCommand("invsee").setExecutor(new CommandInvsee());
        getCommand("coins").setExecutor(new CommandCoins());
        getCommand("stats").setExecutor(new CommandStats());
        getCommand("quest").setExecutor(new CommandQuest());
        getCommand("quests").setExecutor(new CommandQuests());
        getCommand("balance").setExecutor(new CommandBalance());
        getCommand("mute").setExecutor(new CommandMute());
        getCommand("unmute").setExecutor(new CommandUnmute());
        getCommand("inf").setExecutor(new CommandInfractions());
        getCommand("ban").setExecutor(new CommandBan());
        getCommand("unban").setExecutor(new CommandUnban());
        getCommand("warn").setExecutor(new CommandWarn());
        getCommand("kick").setExecutor(new CommandKick());
        getCommand("mid").setExecutor(new CommandMid());
        getCommand("pvp").setExecutor(new CommandPVP());
        getCommand("whois").setExecutor(new CommandWhois());
        getCommand("redeem").setExecutor(new CommandRedeem());
        getCommand("progress").setExecutor(new CommandProgress());
        getCommand("coordstick").setExecutor(new CommandStickcoord());
        getCommand("npc").setExecutor(new CommandNPC());
        getCommand("questmanager").setExecutor(new CommandQuestManager());

        FileConfiguration config = this.getConfig();
        config.addDefault("database_user", "Here");
        config.addDefault("database_password", "Here");

        config.options().copyDefaults(true);
        saveConfig();

        new Initializer().init();
    }

    @Override
    public void onDisable(){
        this.getLogger().info("Unloaded!");

        Initializer.disable();
    }

    public static Inferris getInstance() {
        return instance;
    }

    public static FileConfiguration getFileConfig(){
        return instance.getConfig();
    }

    public static void sendGlobalMessage(String message){
        for(Player all : Bukkit.getServer().getOnlinePlayers()){
            all.sendMessage(message);
        }
    }
}
