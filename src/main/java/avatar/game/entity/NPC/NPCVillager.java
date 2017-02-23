package avatar.game.entity.npc;

import avatar.Avatar;
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
    private Entity entity;

    public NPCVillager(Location location, Villager.Profession profession, String name) {
        Avatar.INSTANCE.getEntityManager().add(this);
        this.location = location;

        entity = location.getWorld().spawnEntity(location, EntityType.VILLAGER);

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

    @Override
    public Entity getEntity(){
        return entity;
    }

    @Override
    public void remove(){
        Avatar.INSTANCE.getEntityManager().remove(this);
        entity.remove();
    }

    @Override
    public Location getLocation() {
        return location;
    }
}
