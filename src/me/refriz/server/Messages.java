package me.refriz.server;

import me.refriz.Inferris;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public enum Messages {
    SPACER(" "),
    SPACER_RESET(ChatColor.RESET + " "),
    NO_PERM(ChatColor.RED + "No permission"),
    RANK_ERROR(ChatColor.RED + "An error occurred. Details: " + ChatColor.WHITE + ""),
    SPECIAL(ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + ";;; "),
    QUEST_FINISH(ChatColor.GREEN + "Quest completed: " + ChatColor.YELLOW),
    QUEST_ASSIGN(ChatColor.GREEN + "Quest assigned: " + ChatColor.YELLOW),
    QUEST_UNASSIGN(ChatColor.GREEN + "Quest unassigned: " + ChatColor.YELLOW),
    QUEST_NEW(Messages.SPECIAL.getMessage() + ChatColor.GREEN + " New quest discovered: " + ChatColor.YELLOW),
    QUEST_NAME(ChatColor.RESET + "" + ChatColor.ITALIC),
    INSUFFICIENT_BALANCE(ChatColor.RED + "Insufficient balance!"),

    ENTER_APARTMENT(ChatColor.YELLOW + "You have entered your apartment"),
    EXIT_APARTMENT(ChatColor.YELLOW + "You have left your apartment"),
    NEW_APARTMENT(ChatColor.GREEN + "This is your apartment!");

    private final String message;

    Messages(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static class Actions{
        public static void unlockItem(Player player, String text, Sound sound1, Sound sound2, Float pitch1, Float pitch2, long waitTime, boolean reward){
            BukkitScheduler scheduler = Bukkit.getScheduler();

            player.sendMessage(ChatColor.AQUA + "Accessing IC Database...");
            PlayerUtils.playSound(player, sound1, pitch1);
            scheduler.scheduleSyncDelayedTask(Inferris.getInstance(), new Runnable() {
                @Override
                public void run() {
                    player.sendMessage(text + ChatColor.YELLOW + " purchased!");
                    PlayerUtils.playSound(player, sound2, pitch2);

                    if(reward){
                        PlayerUtils.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.7F);
                        player.sendMessage(Messages.SPECIAL.getMessage() + ChatColor.GREEN + "Unlocked " + text + ChatColor.GREEN + "!");
                    }
                }
            }, waitTime);
        }
    }
}
