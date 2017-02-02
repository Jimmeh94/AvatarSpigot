package avatar.game.entity.hologram;

import avatar.Avatar;
import net.minecraft.server.v1_11_R1.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.List;

public class ServerHologram extends Hologram{

    public ServerHologram(Location spawnAt, List<String> name) {
        super();

        Location spawn = spawnAt.clone();
        for(int i = 0; i < name.size(); i++){
            spawn.setY(spawnAt.getY() - (i * 0.25));
            stands.add((ArmorStand) spawnAt.getWorld().spawnEntity(spawn, EntityType.ARMOR_STAND));
            EntityArmorStand entityArmorStand = (EntityArmorStand)((CraftArmorStand)stands.get(i)).getHandle();
            entityArmorStand.setInvisible(true);
            stands.get(i).setGravity(false);
            stands.get(i).teleport(spawn);
            stands.get(i).setCustomNameVisible(true);
            stands.get(i).setCustomName(name.get(i));
            /*NBTTagCompound nbtTagCompound = entityArmorStand.getNBTTag();
            if(nbtTagCompound == null){
                nbtTagCompound = new NBTTagCompound();
            }
            nbtTagCompound.setBoolean("Invulnerable", true);*/
        }
        Avatar.INSTANCE.getHologramManager().add(this);
    }

    public void remove() {
        stands.forEach(org.bukkit.entity.ArmorStand::remove);
    }
}
