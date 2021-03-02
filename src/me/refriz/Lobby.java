package me.refriz;

import me.refriz.lobby.Itemdata;
import me.refriz.server.States;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Lobby {

    public void send(Player player){
        this.deployItems(player);
        States.getLobby().add(player.getName());
    }

    public void deployItems(Player player){
        Inventory inventory = player.getInventory();
        inventory.setItem(4 , Itemdata.getGames());
        inventory.setItem(8, Itemdata.getFun());
    }
}
