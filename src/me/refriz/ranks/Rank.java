package me.refriz.ranks;

import me.refriz.Inferris;
import me.refriz.events.RankReceiver;
import me.refriz.server.Messages;
import me.refriz.server.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

import static me.refriz.Inferris.*;

public enum Rank {
    ADMIN(ChatColor.RED + "[Admin]", null, ChatColor.RED, 3, Types.getAdmin()),
    MOD(ChatColor.DARK_GREEN + "[Mod]", null, ChatColor.DARK_GREEN, 2, Types.getMod()),
    HELPER(ChatColor.BLUE + "[Helper]", null, ChatColor.BLUE, 1, Types.getHelper()),
    BUILDER(ChatColor.YELLOW + "[Builder]", ChatColor.YELLOW + "[B]", ChatColor.YELLOW, 1, Types.getBuilder()),
    DONOR2(ChatColor.AQUA + "[Donor 2]", ChatColor.AQUA + "[D2]", ChatColor.WHITE, 1, Types.getDonor2()),
    DONOR(ChatColor.AQUA + "[Donor]", ChatColor.AQUA + "[E]", ChatColor.WHITE, 1, Types.getDonor()),
    NONE("", null, ChatColor.GRAY, 0, Types.getNone());

    private final String prefix;
    private final String shortPrefix;
    private final ChatColor nameColor;
    private final int branchID;
    private final List<Player> rank;

    Rank(String prefix, String shortPrefix, ChatColor nameColor, int branchID, List<Player> rank) {
        this.prefix = prefix;
        this.shortPrefix = shortPrefix;
        this.nameColor = nameColor;
        this.branchID = branchID;
        this.rank = rank;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getShortPrefix() {
        return shortPrefix;
    }

    public ChatColor getNameColor() {
        return nameColor;
    }

    public int getBranchID() {
        return branchID;
    }

    public List<Player> getRank() {
        return rank;
    }

    public static class Types {
        private static final List<Player> admin = new ArrayList<>();
        private static final List<Player> mod = new ArrayList<>();
        private static final List<Player> helper = new ArrayList<>();
        private static final List<Player> builder = new ArrayList<>();
        private static final List<Player> donor2 = new ArrayList<>();
        private static final List<Player> donor = new ArrayList<>();
        private static final List<Player> none = new ArrayList<>();

        public static List<Player> getAdmin() {
            return admin;
        }

        public static List<Player> getMod() {
            return mod;
        }

        public static List<Player> getHelper() {
            return helper;
        }

        public static List<Player> getBuilder() {
            return builder;
        }

        public static List<Player> getDonor2() {
            return donor2;
        }

        public static List<Player> getDonor() {
            return donor;
        }

        public static List<Player> getNone() {
            return none;
        }
    }

    public static void deployTeams() {
        mainScoreboard = Inferris.getInstance().getServer().getScoreboardManager().getMainScoreboard();

        adminTeam = mainScoreboard.registerNewTeam("1-Admin");
        modTeam = mainScoreboard.registerNewTeam("2-Moderator");
        helperTeam = mainScoreboard.registerNewTeam("3-Helper");
        donor2Team = mainScoreboard.registerNewTeam("4-Donor2");
        donor1Team = mainScoreboard.registerNewTeam("5-Donor1");
        noneTeam = mainScoreboard.registerNewTeam("9-None");

        adminTeam.setPrefix(ADMIN.getPrefix() + Messages.SPACER.getMessage());
        modTeam.setPrefix(MOD.getPrefix() + Messages.SPACER.getMessage() + MOD.getNameColor());
        helperTeam.setPrefix(HELPER.getPrefix() + Messages.SPACER.getMessage());
        donor2Team.setPrefix(DONOR2.getPrefix() + Messages.SPACER.getMessage());
        donor1Team.setPrefix(DONOR.getPrefix() + Messages.SPACER.getMessage());
        noneTeam.setPrefix(NONE.getPrefix() + Messages.SPACER.getMessage());

        adminTeam.setColor(ADMIN.getNameColor());
        modTeam.setColor(MOD.getNameColor());
        helperTeam.setColor(HELPER.getNameColor());
        donor2Team.setColor(DONOR2.getNameColor());
        donor1Team.setColor(DONOR.getNameColor());
        noneTeam.setColor(NONE.getNameColor());
    }

    public static String getRankTag(Player player) {

        String prefix = null;

        int donorLevel = PlayerData.getBranchNumber(player, "donor");
        int staffLevel = PlayerData.getBranchNumber(player, "staff");
        int builderLevel = PlayerData.getBranchNumber(player, "builder");

        if (staffLevel == 3) {
            prefix = ADMIN.getPrefix();
        }

        if (donorLevel == 0 && staffLevel == 0) {
            prefix = NONE.getPrefix();
        }

        if (donorLevel == 0) {

            if (staffLevel == 1) {
                prefix = HELPER.getPrefix();
            }
            if (staffLevel == 2) {
                prefix = MOD.getPrefix();
            }
        }

        if (donorLevel == 1) {

            if (staffLevel == 0) {
                prefix = DONOR.getPrefix();
            }
            if (staffLevel == 1) {
                prefix = HELPER.getPrefix();
            }
            if (staffLevel == 2) {
                prefix = MOD.getPrefix();
            }
        }

        if (donorLevel == 2) {

            if (staffLevel == 0) {
                prefix = DONOR2.getPrefix();
            }
            if (staffLevel == 1) {
                prefix = HELPER.getPrefix();
            }
            if (staffLevel == 2) {
                prefix = MOD.getPrefix();
            }
        }
        return prefix;
    }

    public static void refresh(Player player) {
        PlayerData.getDonorBranchID().remove(player.getName());
        PlayerData.getStaffBranchID().remove(player.getName());
        PlayerData.getBuilderBranchID().remove(player.getName());

        Rank.ADMIN.getRank().remove(player);
        Rank.MOD.getRank().remove(player);
        Rank.HELPER.getRank().remove(player);
        Rank.BUILDER.getRank().remove(player);
        Rank.DONOR2.getRank().remove(player);
        Rank.DONOR.getRank().remove(player);
        Rank.NONE.getRank().remove(player);

        new Tab().removeEntries(player);
        new RankReceiver().receive(player);
    }
}