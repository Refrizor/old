package me.aziah.commands;

import me.aziah.server.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandStickcoord implements CommandExecutor,Listener {

    private static List<String> list = new ArrayList<>();
    private static ItemStack stick = new ItemStack(Material.STICK);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if(PlayerData.getStaffBranchID(player) >=3){

            if(!list.contains(player.getName())){
                list.add(player.getName());

                player.getInventory().addItem(getStick());
            }else{
                list.remove(player.getName());
                player.getInventory().remove(getStick());
            }
        }
        return true;
    }

    public static ItemStack getStick() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Coordinate stick");
        stick.setItemMeta(itemMeta);
        return stick;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem().getItemMeta().equals(getStick().getItemMeta())){
                if (list.contains(event.getPlayer().getName())) {
                    Block block = event.getClickedBlock();

                    event.getPlayer().sendMessage("X: " + block.getX() + ", Y: " + block.getY() + ", Z: " + block.getZ());
                }
            }
        }
    }
}
