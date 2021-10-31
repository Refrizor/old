package me.aziah.server;

import me.aziah.Inferris;
import me.aziah.commands.CommandMid;
import me.aziah.commands.CommandStickcoord;
import me.aziah.events.ChatEvent;
import me.aziah.events.Cinematics;
import me.aziah.events.JoinEvent;
import me.aziah.events.QuitEvent;
import me.aziah.lobby.Lobby;
import me.aziah.midstforth.events.areas.LabEvents;
import me.aziah.midstforth.events.LookEvent;
import me.aziah.midstforth.Regions;
import me.aziah.midstforth.events.WalkEvent;
import me.aziah.midstforth.engines.EngineMenu;
import me.aziah.midstforth.menus.Accelerator;
import me.aziah.midstforth.npc.NPCEvents;
import me.aziah.midstforth.quests.list.QuestIntro;
import me.aziah.minigames.pvp.PVPEvents;
import me.aziah.ranks.Rank;
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
        pluginManager.registerEvents(new NPCEvents(), Inferris.getInstance());
        pluginManager.registerEvents(new LabEvents(), Inferris.getInstance());
        pluginManager.registerEvents(new WalkEvent(), Inferris.getInstance());
        pluginManager.registerEvents(new CommandStickcoord(), Inferris.getInstance());

        //Midstforth
        pluginManager.registerEvents(new Regions(), Inferris.getInstance());
        pluginManager.registerEvents(new Accelerator(), Inferris.getInstance());
        pluginManager.registerEvents(new QuestIntro(), Inferris.getInstance());
        pluginManager.registerEvents(new CommandMid(), Inferris.getInstance());
        pluginManager.registerEvents(new LookEvent(), Inferris.getInstance());
        pluginManager.registerEvents(new Cinematics(), Inferris.getInstance());

        //PVP
        pluginManager.registerEvents(new PVPEvents(), Inferris.getInstance());

        Rank.deployTeams();

        try{
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
        Inferris.affiliate.unregister();
        Inferris.noneTeam.unregister();
    }
}
