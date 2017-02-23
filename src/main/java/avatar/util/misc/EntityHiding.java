package avatar.util.misc;

import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class EntityHiding {

    public static Optional<Entity> findEntity(UUID entityID){
        for(Entity entity: Bukkit.getWorlds().get(0).getEntities()){
            if(entity.getUniqueId() == entityID){
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    public static void hidePlayer(Player toHide){
        for(Player player: Bukkit.getOnlinePlayers()){
                player.hidePlayer(toHide);
                toHide.hidePlayer(player);
        }
    }

    public static void showPlayer(Player toShow){
        for(Player player: Bukkit.getOnlinePlayers()){
                player.showPlayer(toShow);
                toShow.showPlayer(player);
        }
    }

    public static void hideEntity(Player player, Entity entity){
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        net.minecraft.server.v1_11_R1.Entity other = ((CraftEntity)entity).getHandle();
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(other.getId()));
    }

    public static void showEntity(Player player, Entity entity){
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        net.minecraft.server.v1_11_R1.Entity other = ((CraftEntity)entity).getHandle();
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutSpawnEntityLiving((EntityLiving) other));
    }

}
