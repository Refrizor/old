package me.refriz.minigames.pvp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Teams {

    // Init the Map that stores all the players and
    // in which team they're in
    private static final Map<UUID, Team> teams = new HashMap<>();

    // The function which takes in a List of players and stores
    // them equally to the teams. All the teams should be included
    // inside the Team enum. This algorithm is dynamic and supports
    // multiple teams (e.g. 4 or 2)
    public static void sortPlayers(List<Player> players) {

        // Let's just mix the given player list, this isn't mandatory however
        Collections.shuffle(players);

        // Let's keep track on in which team are we currently
        int currentTeamIndex = 0;

        // Loop through the players
        for(Player player : players) {

            // If a player is already in a team, just skip them,
            // this is for the support for adding players manually
            // before-hand
            if(isPlayerInTeam(player)) continue;

            // How many teams are there available
            int teamsSize = Team.values().length;

            // The current team we're in
            Team currentTeam = Team.values()[currentTeamIndex];

            // Add the player to the team in hand
            addPlayerToTeam(player, currentTeam);

            // Add one to the index, so we'll move on to
            // the next team
            currentTeamIndex += 1;
            // If we're looped through all the teams already,
            // let's just start over
            if(currentTeamIndex >= teamsSize) {
                currentTeamIndex = 0;
            }

        }

    }

    public static void addPlayerToTeam(Player player, Team team) {
        teams.put(player.getUniqueId(), team);
    }

    // Function to fetch the current team of a player,
    // returns null if the player isn't in any team
    public static Team getTeamOfPlayer(Player player) {

        // Check if player is in a team
        if(isPlayerInTeam(player)) {
            // Return the player's team
            return teams.get(player.getUniqueId());
        }
        return null;

    }

    // A function to add a player to a team. Returns a boolean,
    // to tell if it was successful or not
    public static boolean removePlayerFromTeam(Player player) {

        // Check if player is in a team
        if(isPlayerInTeam(player)) {
            teams.remove(player.getUniqueId());

            // Add a message, optionally
        }

        return false;

    }

    public static boolean isPlayerInTeam(Player player) {
        return teams.containsKey(player.getUniqueId());
    }

    // This function just gets all the players inside
    // a certain team
    public static List<Player> getPlayersInTeam(Team team) {

        List<Player> players = new ArrayList<>();

        for(Map.Entry<UUID, Team> entry : teams.entrySet()) {
            // The player in hand
            Player player = Bukkit.getPlayer(entry.getKey());
            // If a player is offline, disregard them
            if(player == null) continue;

            // If the looped player's team is the same
            // as inputted in the function, add them to
            // our ArrayList
            if(entry.getValue() == team) {
                players.add(player);
            }

        }

        // Finally, just return the List we created in the beginning
        return players;

    }

    // Just the enum for handling the teams
    // The display name for the team is also
    // stored inside this enum. But that's up to
    // you to use them
    public enum Team {

        RED("§c§lRED"),
        BLUE("§9§lBLUE");

        String displayName;
        Team(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}