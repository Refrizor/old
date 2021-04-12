package me.refriz.midstforth.engines;

import me.refriz.midstforth.Economy;
import me.refriz.midstforth.Midstforth;
import me.refriz.server.DatabaseConnector;
import me.refriz.server.Messages;
import me.refriz.server.PlayerUtils;
import me.refriz.server.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class EngineMenu implements Listener {

    public void deploy(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9, EngineItemdata.getEngineMenu());
        inventory.setItem(0, new EngineItemdata().getEngine1(player));
        inventory.setItem(1, new EngineItemdata().getEngine2(player));
        inventory.setItem(2, new EngineItemdata().getEngine3(player));

        player.openInventory(inventory);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView inventoryView = event.getView();

        if (inventoryView.getTitle().equalsIgnoreCase(EngineItemdata.getEngineMenu())) {
            event.setCancelled(true);
            int getEngine = Midstforth.getEngine(player);
            int getMoney = Economy.retrieve(player);

            if (PlayerUtils.clickedMenuItem(event, Engines.ENGINE_1.getName())){

                if(getEngine == 1){
                }
            }

            if(PlayerUtils.clickedMenuItem(event, Engines.ENGINE_2.getName())){
                if(getEngine < 2){
                    if(getMoney >= Engines.ENGINE_2.getCost()){
                        try{
                            Connection connection = DatabaseConnector.getConnection();
                            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `engines` SET `type`= 2 WHERE `uuid` = '" + player.getUniqueId() + "'");
                            preparedStatement.execute();
                            this.unlock(player, Engines.ENGINE_2.getName(), Engines.ENGINE_2.getCost(), inventoryView);
                        }catch(Exception e){
                            e.printStackTrace();
                            player.sendMessage(e.getMessage());
                        }
                    }else{
                        player.sendMessage(Messages.INSUFFICIENT_BALANCE.getMessage());
                    }
                }
            }

            if(PlayerUtils.clickedMenuItem(event, Engines.ENGINE_3.getName())){
                if(getEngine < 3){
                    if(getMoney >= Engines.ENGINE_3.getCost()){
                        Economy.remove(player, Engines.ENGINE_3.getCost());
                        try{
                            Connection connection = DatabaseConnector.getConnection();
                            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `engines` SET `type`= 3 WHERE `uuid` = '" + player.getUniqueId() + "'");
                            preparedStatement.execute();
                            this.unlock(player, Engines.ENGINE_3.getName(), Engines.ENGINE_3.getCost(), inventoryView);
                        }catch(Exception e){
                            e.printStackTrace();
                            player.sendMessage(e.getMessage());
                        }
                    }else{
                        player.sendMessage(Messages.INSUFFICIENT_BALANCE.getMessage());
                    }
                }
            }
        }
    }

    public void unlock(Player player, String engineName, int balanceRemoval, InventoryView view){

        Messages.Actions.unlockItem(player, engineName, Sounds.QUERY.getSound(),
                Sounds.SUCCESS.getSound(),
                Sounds.QUERY.getPitch(),
                Sounds.SUCCESS.getPitch(), 40L, true);

        Economy.remove(player, balanceRemoval);
        view.close();
    }
}
