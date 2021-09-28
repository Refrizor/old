package me.aziah.commands;

import me.aziah.Inferris;
import me.aziah.events.Cinematics;
import me.aziah.midstforth.Regions;
import me.aziah.midstforth.quests.list.QuestIntro;
import me.aziah.server.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;

import java.util.List;

public class CommandMid implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        int length = args.length;

        if (length == 1) {
            if (args[0].equalsIgnoreCase("leaveap")) {
                Regions.exitApartment(player, true);
            }
            if (args[0].equalsIgnoreCase("enterap")) {
                Regions.enterApartment(player, true);
            }
            if (args[0].equalsIgnoreCase("intro")) {
                QuestIntro.deploy(player);
            }
            if (args[0].equalsIgnoreCase("stopintro")) {
                Regions.exitApartment(player, true);
            }
            if (args[0].equalsIgnoreCase("cinematic")) {
                new Cinematics().introduction(player);
            }
            if(args[0].equalsIgnoreCase("test")){

                World world = Bukkit.getWorld("world");
                Location location = new Location(world, 531, 66, -812);
                List<Entity> entities = (List<Entity>) world.getNearbyEntities(location, 10, 10, 10);

                for (Entity entity : entities) {

                    if(entity.getLocation().distanceSquared(player.getLocation()) > 10) continue; // All entities within a sphere

                    if(entity instanceof Player){
                        player.sendMessage("Yep!");

                    }
                }
            }

            if(args[0].equalsIgnoreCase("reset")){
                player.setGravity(true);
                player.setWalkSpeed(0.2F);
                player.setFlying(false);
                player.setFlySpeed(0.1F);
                player.resetPlayerTime();
                player.resetPlayerWeather();
                Cinematics.getInCinematic().remove(player.getUniqueId());
            }
        }
        return true;
    }

    @EventHandler

    public void onFly(EntityTargetLivingEntityEvent event){
        if(event.getTarget().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getTarget();
            if (player.getName().equalsIgnoreCase("Refrizor")) {
                event.setCancelled(true);
            }
        }
    }
}