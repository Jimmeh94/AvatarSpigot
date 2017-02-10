package avatar.game.entity.npc.nms;

import avatar.util.misc.NMSUtils;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.LinkedHashSet;

public class CustomZombie extends EntityZombie{

    public CustomZombie(World world, Location where) {
        super(((CraftWorld) world).getHandle());

        LinkedHashSet goalB = (LinkedHashSet)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();

        LinkedHashSet goalC = (LinkedHashSet)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();

        LinkedHashSet targetB = (LinkedHashSet)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        targetB.clear();

        LinkedHashSet targetC = (LinkedHashSet)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        targetC.clear();

        setPosition(where.getX(), where.getY(), where.getZ());
        //Avatar.INSTANCE.getEntityManager().add(this);
        ((CraftWorld) world).getHandle().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        System.out.println("Zombie");
    }
}
