package me.aziah.lobby.mysterybox;

import me.aziah.server.DatabaseHandler;
import me.aziah.server.SQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Mysterybox {

    private final ArrayList<Integer> rating = new ArrayList<>();

    public static void addBox(OfflinePlayer player, String type, int rating) {
        SQLHandler.action("INSERT INTO `mystery_boxes`(`uuid`, `type`, `rating`) VALUES('" + player.getUniqueId() + "', '" + type + "', " + rating + ")");
    }

    public static void open(OfflinePlayer player) {

    }

    static int index = -1;

    public void checkBoxes(Player player) {
        try {
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT rating FROM `mystery_boxes` WHERE `uuid` = '" + player.getUniqueId() + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            int amount = 0;

            Inventory inventory = Bukkit.createInventory(null, 9, "Mystery Boxes");


            while (resultSet.next()) {
                amount++;
                index++;
                int rating = resultSet.getInt(1); //gets rating for each one


                switch (rating) {
                    case 1:
                        getRating().add(0);
                        break;
                    case 2:
                        getRating().add(1);
                        break;
                    case 3:
                        getRating().add(2);
                        break;
                }

                ItemStack box = new ItemStack(Material.ENDER_CHEST);
                ItemMeta itemMeta = box.getItemMeta();

                ArrayList<String> lore = new ArrayList<>();
                int finalRating = getRating().get(index) + 1;
                lore.add("Rating: " + finalRating);
                itemMeta.setLore(lore);

                box.setItemMeta(itemMeta);

                inventory.setItem(index, box);

                //0: goes up until hits amount


                player.openInventory(inventory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getRating() {
        return rating;
    }
}