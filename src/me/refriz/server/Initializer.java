package me.refriz.server;

import me.refriz.Inferris;
import me.refriz.events.ChatEvent;
import me.refriz.events.JoinEvent;
import me.refriz.events.QuitEvent;
import me.refriz.lobby.Lobby;
import me.refriz.midstforth.engines.EngineMenu;
import me.refriz.ranks.Rank;
import org.bukkit.plugin.PluginManager;

public class Initializer {

    public static void init(){
        PluginManager pluginManager = Inferris.getInstance().getServer().getPluginManager();

        pluginManager.registerEvents(new JoinEvent(), Inferris.getInstance());
        pluginManager.registerEvents(new QuitEvent(), Inferris.getInstance());
        pluginManager.registerEvents(new ChatEvent(), Inferris.getInstance());
        pluginManager.registerEvents(new Lobby(), Inferris.getInstance());
        pluginManager.registerEvents(new SpamFilter(), Inferris.getInstance());
        pluginManager.registerEvents(new EngineMenu(), Inferris.getInstance());

        Rank.deployTeams();

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
