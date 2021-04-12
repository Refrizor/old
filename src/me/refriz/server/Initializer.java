package me.refriz.server;

import me.refriz.Inferris;
import me.refriz.events.ChatEvent;
import me.refriz.events.JoinEvent;
import me.refriz.events.QuitEvent;
import me.refriz.lobby.Lobby;
import me.refriz.midstforth.Regions;
import me.refriz.midstforth.engines.EngineMenu;
import me.refriz.midstforth.quests.Dialogue;
import me.refriz.minigames.pvp.PVPEvents;
import me.refriz.ranks.Rank;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginManager;

public class Initializer {

    public void init(){
        PluginManager pluginManager = Inferris.getInstance().getServer().getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), Inferris.getInstance());
        pluginManager.registerEvents(new QuitEvent(), Inferris.getInstance());
        pluginManager.registerEvents(new ChatEvent(), Inferris.getInstance());
        pluginManager.registerEvents(new Lobby(), Inferris.getInstance());
        pluginManager.registerEvents(new SpamFilter(), Inferris.getInstance());
        pluginManager.registerEvents(new EngineMenu(), Inferris.getInstance());
        pluginManager.registerEvents(new Dialogue(), Inferris.getInstance());

        //Midstforth
        pluginManager.registerEvents(new Regions(), Inferris.getInstance());

        //PVP
        pluginManager.registerEvents(new PVPEvents(), Inferris.getInstance());

        Rank.deployTeams();

        try{
            WorldCreator worldCreator = new WorldCreator("creations");
            WorldManager.load(worldCreator);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void disable(){
        Inferris.adminTeam.unregister();
        Inferris.modTeam.unregister();
        Inferris.helperTeam.unregister();
        Inferris.donor2Team.unregister();
        Inferris.donor1Team.unregister();
        Inferris.noneTeam.unregister();
    }
}
