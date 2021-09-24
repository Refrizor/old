package me.aziah.minigames.pvp;

import org.bukkit.ChatColor;

public enum Messages {
    ERROR_ALREADY_IN_QUEUE(ChatColor.RED + "Error: You are already in the queue."),
    ERROR_NOT_IN_QUEUE(ChatColor.RED + "Error: You are not in the queue."),

    STARTUP(ChatColor.AQUA + "The game has started!"),
    STARTUP_CANCELLED(ChatColor.RED + "Game startup cancelled; not enough people in queue."),

    BLUE_START(ChatColor.BLUE + "You are on the blue team."),
    RED_START(ChatColor.RED + "You are on the red team."),

    GRACE_START("" + ChatColor.AQUA + ChatColor.BOLD + "Grace period has started!"),
    GRACE_END("" + ChatColor.AQUA + ChatColor.RED + "Grace period has ended! " + ChatColor.YELLOW + "Fight!");

    private final String message;
    Messages(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
