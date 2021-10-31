package me.aziah.commands;

import me.aziah.Inferris;
import me.aziah.events.Cinematics;
import me.aziah.midstforth.Regions;
import me.aziah.midstforth.States;
import me.aziah.midstforth.events.areas.Lab_A;
import me.aziah.midstforth.quests.list.QuestIntro;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.lang.reflect.Field;
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
                new QuestIntro().deploy(player);
            }
            if (args[0].equalsIgnoreCase("stopintro")) {
                Regions.exitApartment(player, true);
            }
            if (args[0].equalsIgnoreCase("cinematic")) {
                new Cinematics().introduction(player);
            }
            if(args[0].equalsIgnoreCase("search")){

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

            if(args[0].equalsIgnoreCase("discoveries")){
                if(States.getDiscoveries().containsKey(player.getUniqueId())){
                    if(States.getDiscoveries().containsValue("satellite1")){
                        player.sendMessage(ChatColor.GREEN + "Success");
                    }
                }
            }

            if(args[0].equalsIgnoreCase("bob")){
                        player.sendMessage(States.getDiscoveries().size() + "");
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

            if(args[0].equalsIgnoreCase("tempdiscover")){
                Lab_A.getTemporaryDiscover().add(player.getName());
            }

            if(args[0].equalsIgnoreCase("test")){
                CraftPlayer craftPlayer = (CraftPlayer) player;
                CraftServer craftServer = (CraftServer) Inferris.getInstance().getServer();
                EntityArmorStand armorStand = new EntityArmorStand(craftPlayer.getHandle().getWorld(), -122, 33, -240);
                PacketPlayOutSpawnEntityLiving entity = new PacketPlayOutSpawnEntityLiving(armorStand);

                try{
                    Field field = entity.getClass().getField("a");
                    field.setAccessible(true);
                    field.setInt(entity, 22);
                    field.setAccessible(false);
                }catch(Exception e){
                    e.printStackTrace();
                }
                craftPlayer.getHandle().playerConnection.sendPacket(entity);
            }
        }
        return true;
    }


    public void onFly(EntityTargetLivingEntityEvent event){
        if(event.getTarget().getType() == EntityType.PLAYER) {
            Player player = (Player) event.getTarget();
            if (player.getName().equalsIgnoreCase("Refrizor")) {
                event.setCancelled(true);
            }
        }
    }
}