package me.aziah.server;

import org.bukkit.Bukkit;

public class Server {
    enum Ports{
        MINIGAMES(25566),
        MIDSTFORTH(25567);

        private final int server;
        Ports(int server){
            this.server = server;
        }

        public int getServer() {
            return server;
        }
    }

    public static boolean getPort(int port){
        return Bukkit.getServer().getPort() == port;
    }
}
