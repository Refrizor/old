package me.aziah.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.aziah.midstforth.npc.NPC;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class CommandNPC implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        int length = args.length;

        if (length == 1) {
                if (args[0].equalsIgnoreCase("skeleton")) {

                    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

                    GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
                    gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNjQxNzk4NDU2NjFlYzU4NzljZTNiZDcwNGZhMGQzOGFkMjAwOTllNDIwNmI4NmI5MTg1MjI0MWI0YzZlNCJ9fX0=="));

                    try {
                        Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                        mtd.setAccessible(true);
                        mtd.invoke(skullMeta, gameProfile);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        ex.printStackTrace();
                    }

                    ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
                    leggingsMeta.setColor(Color.fromRGB(29, 29, 29));

                    ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                    LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
                    bootsMeta.setColor(Color.fromRGB(42, 42, 42));

                    leggings.setItemMeta(leggingsMeta);
                    boots.setItemMeta(bootsMeta);
                    itemStack.setItemMeta(skullMeta);
                    LivingEntity livingEntity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.WITHER_SKELETON);
                    livingEntity.setCanPickupItems(false);
                    livingEntity.setRemoveWhenFarAway(false);
                    livingEntity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                    livingEntity.getEquipment().setHelmet(itemStack);
                    livingEntity.getEquipment().setLeggings(leggings);
                    livingEntity.getEquipment().setBoots(boots);

                    livingEntity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
                }

            if (args[0].equalsIgnoreCase("skeleton2")) {

                ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
                gameProfile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTNkNjQxNzk4NDU2NjFlYzU4NzljZTNiZDcwNGZhMGQzOGFkMjAwOTllNDIwNmI4NmI5MTg1MjI0MWI0YzZlNCJ9fX0=="));

                try {
                    Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                    mtd.setAccessible(true);
                    mtd.invoke(skullMeta, gameProfile);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace();
                }

                ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
                leggingsMeta.setColor(Color.fromRGB(29, 29, 29));

                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
                bootsMeta.setColor(Color.fromRGB(42, 42, 42));

                leggings.setItemMeta(leggingsMeta);
                boots.setItemMeta(bootsMeta);
                itemStack.setItemMeta(skullMeta);
                LivingEntity livingEntity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
                livingEntity.setCanPickupItems(false);
                livingEntity.setRemoveWhenFarAway(false);
                livingEntity.getEquipment().setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
                livingEntity.getEquipment().setHelmet(itemStack);
                livingEntity.getEquipment().setLeggings(leggings);
                livingEntity.getEquipment().setBoots(boots);

                livingEntity.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_SWORD));
            }
        }

        if (length == 3) {
            if (args[0].equalsIgnoreCase("villager")) {
                switch(args[1]){
                    case "armorer":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.ARMORER, args[2], true);
                        break;
                    case "butcher":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.BUTCHER, args[2], true);
                        break;
                    case "cartographer":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.CARTOGRAPHER, args[2], true);
                        break;
                    case "cleric":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.CLERIC, args[2], true);
                        break;
                    case "farmer":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.FARMER, args[2], true);
                        break;
                    case "fisherman":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.FISHERMAN, args[2], true);
                        break;
                    case "fletcher":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.FLETCHER, args[2], true);
                        break;
                    case "leatherworker":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.LEATHERWORKER, args[2], true);
                        break;
                    case "librarian":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.LIBRARIAN, args[2], true);
                        break;
                    case "mason":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.MASON, args[2], true);
                        break;
                    case "nitwit":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.NITWIT, args[2], true);
                        break;
                    case "none":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.NONE, args[2], true);
                        break;
                    case "shepherd":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.SHEPHERD, args[2], true);
                        break;
                    case "toolsmith":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.TOOLSMITH, args[2], true);
                        break;
                    case "weaponsmith":
                        NPC.addFriendly(player, EntityType.VILLAGER, Villager.Profession.WEAPONSMITH, args[2], true);
                        break;
                }
            }
        }
        return true;
    }
}