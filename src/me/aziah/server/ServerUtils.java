package me.aziah.server;

import net.minecraft.server.v1_16_R3.EntityLightning;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ServerUtils {

    public void sendLightning(Player player, double x, double y, double z){
        WorldServer worldServer = ((CraftWorld) player.getWorld()).getHandle();
        EntityLightning entityLightning = new EntityLightning(EntityTypes.LIGHTNING_BOLT, worldServer);
        entityLightning.setPositionRaw(x, y, z);

        PacketPlayOutSpawnEntity lightningPacket = new PacketPlayOutSpawnEntity(entityLightning);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(lightningPacket);
    }
}
