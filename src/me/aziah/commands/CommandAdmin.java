package me.aziah.commands;

import me.aziah.lobby.mysterybox.Mysterybox;
import me.aziah.midstforth.States;
import me.aziah.midstforth.npc.NPCEvents;
import me.aziah.midstforth.quests.list.QuestIntro;
import me.aziah.server.WorldManager;
import me.aziah.midstforth.SpacesuitItemdata;
import me.aziah.midstforth.npc.NPCTypes;
import me.aziah.midstforth.quests.Quest;
import me.aziah.ranks.Rank;
import me.aziah.server.Messages;
import me.aziah.midstforth.engines.EngineMenu;
import me.aziah.server.SQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class CommandAdmin implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

            if (player.isOp()) {

                int length = args.length;

                if (length == 1) {

                    if(args[0].equalsIgnoreCase("intro")){
                        new QuestIntro().deploy(player);
                    }
                    if(args[0].equalsIgnoreCase("box")){
                        new Mysterybox().checkBoxes(player);
                    }
                    if(args[0].equalsIgnoreCase("gen")){
                        WorldManager.create("minigames", WorldType.FLAT);
                    }

                    if(args[0].equalsIgnoreCase("tp")){
                        player.teleport(new Location(Bukkit.getWorld("test"), 1, 50, 1));
                    }

                    if(args[0].equalsIgnoreCase("tpmain")){
                        player.teleport(new Location(Bukkit.getWorld("world"), 1, 50, 1));
                    }

                    if(args[0].equalsIgnoreCase("introstop")){
                        for(PotionEffect potionEffect : player.getActivePotionEffects()){
                            player.removePotionEffect(potionEffect.getType());
                        }
                    }

                    if (args[0].equalsIgnoreCase("getrank")) {

                        player.sendMessage(ChatColor.GREEN + "Current rank: " + Rank.getRankTag(player));
                    }

                    if (args[0].equalsIgnoreCase("refresh")) {
                        Rank.refresh(player);
                        player.sendMessage(ChatColor.GREEN + "Refreshed");
                    }

                    if (args[0].equalsIgnoreCase("ship")) {
                        new EngineMenu().deploy(player);
                    }

                    if (args[0].equalsIgnoreCase("size")) {
                        player.sendMessage("" + NPCEvents.getCanClick().size());
                    }
                    if(args[0].equalsIgnoreCase("spacesuit")){
                        player.getInventory().setHelmet(SpacesuitItemdata.getHelmet());
                        player.getInventory().setHelmet(SpacesuitItemdata.getChestplate());
                        player.getInventory().setHelmet(SpacesuitItemdata.getLeggings());
                        player.getInventory().setHelmet(SpacesuitItemdata.getBoots());
                    }
                }
                if(length == 2){

                    if(args[0].equalsIgnoreCase("tp")){
                        player.teleport(new Location(Bukkit.getWorld(args[1]), 1, 50, 1));
                    }

                    if(args[0].equalsIgnoreCase("nukemid")){
                        Player target = Bukkit.getPlayer(args[1]);

                        SQLHandler.action("DELETE FROM `locations` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `midstforth_economy` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `played_midstforth` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `quests` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `active_quests` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `completed_quests` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `engines` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `progression_states` WHERE `uuid` = '" + target.getUniqueId() + "'");
                        SQLHandler.action("DELETE FROM `ranks` WHERE `uuid` = '" + target.getUniqueId() + "'");



                        player.sendMessage(ChatColor.GREEN + "Operation successful");
                        target.kickPlayer(ChatColor.GREEN + "Data reset");
                    }
                    if(args[0].equalsIgnoreCase("nukerank")){
                        Player target = Bukkit.getPlayer(args[1]);

                        SQLHandler.action("DELETE FROM `ranks` WHERE `uuid` = '" + target.getUniqueId() + "'");

                        player.sendMessage(ChatColor.GREEN + "Operation successful");
                        target.kickPlayer(ChatColor.GREEN + "Data reset");
                    }

                    if(args[0].equalsIgnoreCase("finishquest")){
                        Quest.completeQuest(player, args[1], "John", 20);
                    }
                }
            } else {
                player.sendMessage(Messages.NO_PERM.getMessage());
            }
        return true;
    }
}
