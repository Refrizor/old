package me.refriz.midstforth.engines;

import me.refriz.server.DatabaseConnector;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EngineItemdata {

    private static final ItemStack engine3 = new ItemStack(Material.EMERALD_BLOCK);
    private static final ItemStack engine2 = new ItemStack(Material.GOLD_BLOCK);
    private static final ItemStack engine1 = new ItemStack(Material.IRON_BLOCK);

    private static final String engineMenu = ChatColor.BLACK + "Engine Menu";
    private static final String owned = ChatColor.GREEN + "Owned";
    private static final String purchase = ChatColor.RED + "Cost: ";

    public static ItemStack getEngine3(Player player) {
        ItemMeta itemMeta = engine3.getItemMeta();
        itemMeta.setDisplayName(Engines.ENGINE_3.getName());

        ArrayList<String> lore = new ArrayList<String>();
        String loreText = getEngineType(player, 3);
        lore.add(loreText);
        itemMeta.setLore(lore);

        engine3.setItemMeta(itemMeta);
        return engine3;
    }

    public static ItemStack getEngine2(Player player) {
        ItemMeta itemMeta = engine2.getItemMeta();
        itemMeta.setDisplayName(Engines.ENGINE_2.getName());
        ArrayList<String> lore = new ArrayList<String>();
        String loreText = getEngineType(player, 2);
        lore.add(loreText);
        itemMeta.setLore(lore);

        engine2.setItemMeta(itemMeta);
        return engine2;
    }

    public static ItemStack getEngine1(Player player) {
        ItemMeta itemMeta = engine1.getItemMeta();
        itemMeta.setDisplayName(Engines.ENGINE_1.getName());
        ArrayList<String> lore = new ArrayList<String>();
        String loreText = getEngineType(player, 1);
        lore.add(loreText);
        itemMeta.setLore(lore);

        engine1.setItemMeta(itemMeta);
        return engine1;
    }

    public static String getEngineType(Player player, int type){
        String result = null;
        int engine = 0;

        try{
            Connection connection = DatabaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT type FROM `engines` WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                engine = resultSet.getInt(1);
            }

            if(type == 3){

                if(engine >= 3){
                    result = getOwned();
                }else{
                    result = getPurchase() + Engines.ENGINE_3.getCost();
                }
            }

            if(type == 2){

                if(engine >= 2){
                    result = getOwned();
                }else{
                    result = getPurchase() + Engines.ENGINE_2.getCost();
                }
            }

            if(type == 1){

                if(engine >= 1){
                    result = getOwned();
                }else{
                    result = getPurchase() + Engines.ENGINE_1.getCost();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String getEngineMenu() {
        return engineMenu;
    }

    public static String getOwned() {
        return owned;
    }

    public static String getPurchase() {
        return purchase;
    }
}
