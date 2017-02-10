package avatar.game.entity.npc.nms;

import avatar.Avatar;
import avatar.game.user.User;
import avatar.game.user.stats.presets.DefaultBenderPreset;
import avatar.util.misc.NMSUtils;
import net.minecraft.server.v1_11_R1.*;
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

        //Add invisibility for 1 second so in case this is instanced, it won't blip in and out on
        //players' screens
        this.addEffect(new MobEffect(MobEffects.INVISIBILITY, 1));

        ((CraftWorld) world).getHandle().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        Avatar.INSTANCE.getUserManager().add(new User(this.getUniqueID(), new DefaultBenderPreset()));
        System.out.println("Zombie");
    }
}
