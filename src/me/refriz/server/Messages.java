package me.refriz.server;

import org.bukkit.ChatColor;

public enum Messages {
    SPACER(" "),
    SPACER_RESET(ChatColor.RESET + " "),
    NO_PERM(ChatColor.RED + "No permission"),
    RANK_ERROR(ChatColor.RED + "An error occurred. Details: " + ChatColor.WHITE + ""),
    SPECIAL(ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + ";");

    private final String message;

    Messages(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public enum Midstforth{
        QUEST_FINISH(ChatColor.GREEN + "Quest completed: " + ChatColor.YELLOW),
        QUEST_ASSIGN(ChatColor.GREEN + "Quest assigned: " + ChatColor.YELLOW),
        QUEST_UNASSIGN(ChatColor.GREEN + "Quest unassigned: " + ChatColor.YELLOW),
        QUEST_NEW(Messages.SPECIAL.getMessage() + ChatColor.GREEN + " New quest discovered: " + ChatColor.YELLOW);

        private final String message;

        Midstforth(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
