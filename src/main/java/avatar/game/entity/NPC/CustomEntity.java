package avatar.game.entity.npc;

import avatar.Avatar;
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

public abstract class CustomEntity implements NPC{

    private Location location;
    protected Entity entity;

    protected abstract void onInteract(Player player);

    public CustomEntity(Location where, EntityType type){
        this.location = where;
        Avatar.INSTANCE.getEntityManager().add(this);

        entity = location.getWorld().spawnEntity(location, type);
        entity.getLocation().setYaw(location.getYaw());
        entity.getLocation().setPitch(location.getPitch());
        //To set yaw and pitch:
        entity.teleport(entity.getLocation());
        Avatar.INSTANCE.getUserManager().add(new User(entity.getUniqueId(), new DefaultBenderPreset()));

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
        onInteract(player);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public User getUser() {
        return Avatar.INSTANCE.getUserManager().findUser(entity).get();
    }
}
