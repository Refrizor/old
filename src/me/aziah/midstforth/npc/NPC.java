package me.aziah.midstforth.npc;

import me.aziah.Inferris;
import me.aziah.midstforth.quests.Quest;
import me.aziah.server.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class NPC {

    public static void addFriendly(Player player, EntityType entityType, Villager.Profession profession, String name, boolean isSilent) {
        try {
            LivingEntity livingEntity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), entityType);

            livingEntity.setAI(false);
            livingEntity.setInvulnerable(true);
            livingEntity.setCustomName(name);
            Villager villager = (Villager) livingEntity;
            villager.setProfession(profession);

            if(isSilent){
                villager.setSilent(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void giveQuest(Player player, Object quest, String description, long wait, boolean forceAssign){
        if(!Quest.hasQuest(player, quest)){
            //PlayerUtils.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 0.9F);

            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Quest.addQuest(player, quest, description, forceAssign);
                }
            }, wait);
        }
    }

    public static void greet(Player player, String npc, String message){
        player.sendMessage(ChatColor.AQUA + npc + ChatColor.RESET + " " + message);
        PlayerUtils.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 0.9F);
    }

    public static void greetSad(Player player, String npc, String message){
        player.sendMessage(ChatColor.AQUA + npc + ChatColor.RESET + " " + message);
        PlayerUtils.playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 0.7F);
    }

    public static void greet2(Player player, String npc, String message, String message2){
        BukkitScheduler scheduler = Bukkit.getScheduler();
        player.sendMessage(ChatColor.AQUA + npc + ChatColor.RESET + " " + message);
        PlayerUtils.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 0.9F);

        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.AQUA + npc + ChatColor.RESET + " " + message2);
                PlayerUtils.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 0.9F);
            }
        }, 40L);
    }

    public static void greet3(Player player, String npc, String message, String message2, String message3){
        BukkitScheduler scheduler = Bukkit.getScheduler();
        player.sendMessage(ChatColor.AQUA + npc + ChatColor.RESET + " " + message);
        PlayerUtils.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 0.9F);

        scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.AQUA + npc + ChatColor.RESET + " " + message2);
                PlayerUtils.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 0.9F);

                scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        player.sendMessage(ChatColor.AQUA + npc + ChatColor.RESET + " " + message3);
                        PlayerUtils.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 0.9F);
                    }
                }, 40L);
            }
        }, 40L);
    }

    public static boolean isType(PlayerInteractEntityEvent event, String name) {
        return event.getRightClicked().getName().equalsIgnoreCase(name);
    }
}