package me.aziah.ranks;

import me.aziah.Inferris;
import me.aziah.server.DatabaseHandler;
import me.aziah.server.Messages;
import me.aziah.server.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static me.aziah.Inferris.*;

public enum Rank {
    ADMIN(ChatColor.RED + "[Admin]", null, ChatColor.RED, 3),
    MOD(ChatColor.DARK_GREEN + "[Mod]", null, ChatColor.DARK_GREEN, 2),
    HELPER(ChatColor.BLUE + "[Helper]", null, ChatColor.BLUE, 1),
    BUILDER(ChatColor.YELLOW + "[Builder]", ChatColor.YELLOW + "[B]", ChatColor.YELLOW, 1),
    DONOR2(ChatColor.AQUA + "[Donor 2]", ChatColor.AQUA + "[D2]", ChatColor.WHITE, 2),
    DONOR(ChatColor.AQUA + "[Donor]", ChatColor.AQUA + "[E]", ChatColor.WHITE, 1),
    AFFILIATE(ChatColor.GREEN + "[Affiliate]", ChatColor.GREEN + "[A]", ChatColor.WHITE, 1),
    NONE(ChatColor.AQUA + "[Tester]", null, ChatColor.AQUA, 0);

    private final String prefix;
    private final String shortPrefix;
    private final ChatColor nameColor;
    private final int branchID;

    Rank(String prefix, String shortPrefix, ChatColor nameColor, int branchID) {
        this.prefix = prefix;
        this.shortPrefix = shortPrefix;
        this.nameColor = nameColor;
        this.branchID = branchID;
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


    /*
    should be init, but just initializes teams
     */
    public static void deployTeams() {
        mainScoreboard = Inferris.getInstance().getServer().getScoreboardManager().getMainScoreboard();

        adminTeam = mainScoreboard.registerNewTeam("1-Admin");
        modTeam = mainScoreboard.registerNewTeam("2-Moderator");
        helperTeam = mainScoreboard.registerNewTeam("3-Helper");
        affiliate = mainScoreboard.registerNewTeam("4-Affiliate");
        donor2Team = mainScoreboard.registerNewTeam("5-Donor2");
        donor1Team = mainScoreboard.registerNewTeam("6-Donor1");
        noneTeam = mainScoreboard.registerNewTeam("9-None");

        adminTeam.setPrefix(ADMIN.getPrefix() + Messages.SPACER.getMessage());
        modTeam.setPrefix(MOD.getPrefix() + Messages.SPACER.getMessage() + MOD.getNameColor());
        helperTeam.setPrefix(HELPER.getPrefix() + Messages.SPACER.getMessage());
        affiliate.setPrefix(AFFILIATE.getPrefix() + Messages.SPACER.getMessage());
        donor2Team.setPrefix(DONOR2.getPrefix() + Messages.SPACER.getMessage());
        donor1Team.setPrefix(DONOR.getPrefix() + Messages.SPACER.getMessage());
        noneTeam.setPrefix(NONE.getPrefix() + Messages.SPACER.getMessage());

        adminTeam.setColor(ADMIN.getNameColor());
        modTeam.setColor(MOD.getNameColor());
        helperTeam.setColor(HELPER.getNameColor());
        affiliate.setColor(AFFILIATE.getNameColor());
        donor2Team.setColor(DONOR2.getNameColor());
        donor1Team.setColor(DONOR.getNameColor());
        noneTeam.setColor(NONE.getNameColor());
    }

    public static String getRankTag(OfflinePlayer player) {

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
        PlayerData.getDonorBranch().remove(player.getName());
        PlayerData.getStaffBranch().remove(player.getName());
        PlayerData.getBuilderBranch().remove(player.getName());
        PlayerData.getAffiliateBranch().remove(player.getName());

        new Tab().removeEntries(player);
        new RankReceiver().receive(player);
    }

    public static void update(Player player, String rank, int branchID){
        try{
            Connection connection = DatabaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `ranks` SET `" + rank + "` = " + branchID + " WHERE `uuid` = '" + player.getUniqueId() + "'");
            preparedStatement.execute();

            refresh(player);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}