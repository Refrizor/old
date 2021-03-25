package me.refriz.commands;

import me.refriz.Inferris;
import me.refriz.midstforth.Economy;
import me.refriz.server.DatabaseConnector;
import me.refriz.server.PlayerUtils;
import me.refriz.server.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CommandBalance implements CommandExecutor {

    int id;
    private static final List<String> list = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player player = (Player) sender;

        if(!list.contains(player.getName())) {

            BukkitScheduler scheduler = Bukkit.getScheduler();

            player.sendMessage(ChatColor.AQUA + "Accessing IC database...");
            list.add(player.getName());
            PlayerUtils.playSound(player, Sounds.QUERY.getSound(), Sounds.QUERY.getPitch());


            id = scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                @Override
                public void run() {
                    int cash = Economy.retrieve(player);
                    player.sendMessage(ChatColor.AQUA + "> " + ChatColor.YELLOW + "IC Balance: " + ChatColor.DARK_GREEN + "$" + cash);
                    PlayerUtils.playSound(player, Sounds.SUCCESS.getSound(), Sounds.SUCCESS.getPitch());

                    scheduler.cancelTask(id);
                    list.remove(player.getName());
                }
            }, 30L);
        }else{
            player.sendMessage(ChatColor.RED + "Already connecting to the International Currency database!");
            PlayerUtils.playSound(player, Sounds.ERROR.getSound(), Sounds.ERROR.getPitch());
        }
        return true;
    }
}
