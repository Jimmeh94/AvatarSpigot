package avatar.game.entity.npc;

import avatar.Avatar;
import avatar.game.user.User;
import avatar.util.misc.NMSUtils;
import net.minecraft.server.v1_11_R1.EntityVillager;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.PathfinderGoalSelector;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import java.util.LinkedHashSet;

public abstract class NPCVillager implements NPC{

    /**
     * Profession that causes double interactions: NITWIT
     */

    private Location location;
    protected Entity entity;

    public NPCVillager(Location location, Villager.Profession profession, String name) {
        Avatar.INSTANCE.getEntityManager().add(this);
        this.location = location;

        entity = location.getWorld().spawnEntity(location, EntityType.VILLAGER);

        createUserEntry();

        EntityVillager villager1 = ((CraftVillager)entity).getHandle();
        ((CraftVillager)entity).setProfession(profession);
        villager1.setSilent(true);

        Object goalSelector = villager1.goalSelector;
        LinkedHashSet goalB = (LinkedHashSet) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();

        LinkedHashSet goalC = (LinkedHashSet)NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();

        LinkedHashSet targetB = (LinkedHashSet) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        targetB.clear();

        LinkedHashSet targetC = (LinkedHashSet) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        targetC.clear();

        NBTTagCompound compound = new NBTTagCompound();
        villager1.c(compound);
        compound.setByte("NoAI", (byte) 1);
        villager1.f(compound);
        compound.setBoolean("Invulnerable", true);
        getEntity().setCustomName(name);
        getEntity().setCustomNameVisible(true);
    }

    /**
     * Override this if you want to give it a preset for stats
     */
    protected void createUserEntry(){
        Avatar.INSTANCE.getUserManager().add(new User(entity.getUniqueId()));
    }

    @Override
    public Entity getEntity(){
        return entity;
    }

    @Override
    public void remove(){
        Avatar.INSTANCE.getEntityManager().remove(this);
        Avatar.INSTANCE.getUserManager().remove(Avatar.INSTANCE.getUserManager().find(entity.getUniqueId()).get());
        entity.remove();
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
