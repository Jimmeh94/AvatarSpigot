package avatar.game.entity.npc.nms;

import avatar.util.misc.NMSUtils;
import net.minecraft.server.v1_11_R1.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;

import java.util.Map;

public enum EntityTypes {

    //NAME("Entity name", Entity ID, customClass.class)
    CUSTOM_ZOMBIE("Zombie", 54, CustomZombie.class);

    private EntityTypes(String s, int i, Class<? extends Entity> custom){
        addToMaps(custom, s, i);
    }

    public static void spawnEntity(Entity entity, Location location){
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        ((CraftWorld)location.getWorld()).getHandle().addEntity(entity);
    }

    private static void addToMaps(Class c, String s, int id){
        ((Map)NMSUtils.getPrivateField("c", net.minecraft.server.v1_11_R1.EntityTypes.class, null)).put(s, c);
        ((Map)NMSUtils.getPrivateField("d", net.minecraft.server.v1_11_R1.EntityTypes.class, null)).put(c, s);
        ((Map)NMSUtils.getPrivateField("f", net.minecraft.server.v1_11_R1.EntityTypes.class, null)).put(c, Integer.valueOf(id));
    }
}
