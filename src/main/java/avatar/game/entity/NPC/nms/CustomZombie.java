package avatar.game.entity.npc.nms;

import avatar.Avatar;
import avatar.game.entity.npc.NPC;
import avatar.game.user.User;
import avatar.game.user.stats.presets.DefaultBenderPreset;
import avatar.util.misc.NMSUtils;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;

public class CustomZombie implements NPC{

    private Location location;
    private Entity entity;

    public CustomZombie(Location where){
        this.location = where;
        entity = location.getWorld().spawnEntity(location, EntityType.ZOMBIE);

        EntityZombie villager1 = ((CraftZombie)entity).getHandle();
        villager1.setSilent(true);

        Object goalSelector = villager1.goalSelector;
        LinkedHashSet goalB = (LinkedHashSet)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();

        LinkedHashSet goalC = (LinkedHashSet)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();

        LinkedHashSet targetB = (LinkedHashSet)NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        targetB.clear();

        LinkedHashSet targetC = (LinkedHashSet)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        targetC.clear();

        Avatar.INSTANCE.getUserManager().add(new User(entity.getUniqueId(), new DefaultBenderPreset()));
        Avatar.INSTANCE.getEntityManager().add(this);
    }

    @Override
    public void remove(){
        Avatar.INSTANCE.getUserManager().remove(Avatar.INSTANCE.getUserManager().find(entity.getUniqueId()).get());
        Avatar.INSTANCE.getEntityManager().remove(this);
        entity.remove();
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public void onInteraction(Player player) {

    }

    @Override
    public Location getLocation() {
        return location;
    }
}
