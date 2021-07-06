package me.aziah.midstforth.menus;

import me.aziah.server.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.ArrayList;
import java.util.List;

public class Accelerator implements Listener {

    private static final List<String> poweredOn = new ArrayList<>();
    private static final List<String> cooldown = new ArrayList<>();

    private final String name = ChatColor.BLACK + "Control Panel";

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getClickedBlock().getType() == Material.OBSERVER) {
                    Block block = event.getClickedBlock();

                    if (block.getX() == -159 && block.getY() == 42 && block.getZ() == -202) {
                        Inventory inventory = Bukkit.createInventory(null, 9, name);
                        inventory.setItem(3, AcceleratorItemdata.getEnable());
                        inventory.setItem(5, AcceleratorItemdata.getDisable());
                        event.getPlayer().openInventory(inventory);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView inventoryView = event.getView();

        if (inventoryView.getTitle().equals(name)) {
            event.setCancelled(true);
            inventoryView.close();

            if(PlayerUtils.clickedMenuItem(event, AcceleratorItemdata.getEnable().getItemMeta().getDisplayName())){
                if(!getPoweredOn().contains(player.getName())){
                    getPoweredOn().add(player.getName());

                    player.playSound(player.getLocation(), "starting", 10F, 1F);
                }
            }

            if(PlayerUtils.clickedMenuItem(event, AcceleratorItemdata.getDisable().getItemMeta().getDisplayName())){
                if(getPoweredOn().contains(player.getName())){
                }
            }
        }
    }

    public static List<String> getCooldown() {
        return cooldown;
    }

    public static List<String> getPoweredOn() {
        return poweredOn;
    }
}