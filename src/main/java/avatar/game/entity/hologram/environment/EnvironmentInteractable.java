package avatar.game.entity.hologram.environment;

import avatar.game.entity.hologram.Hologram;
import avatar.manager.ServerEInteractableManager;
import net.minecraft.server.v1_11_R1.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class EnvironmentInteractable extends Hologram {

    private final ServerEInteractableManager.ServerEIReference reference;
    private Material material;

    public EnvironmentInteractable(ServerEInteractableManager.ServerEIReference reference, Material material) {
        super();

        this.reference = reference;
        this.material = material;
    }

    public ServerEInteractableManager.ServerEIReference getReference() {
        return reference;
    }

    public void spawn(){
        Location spawn = reference.getLocation().clone();
        stands.add((ArmorStand) spawn.getWorld().spawnEntity(spawn, EntityType.ARMOR_STAND));
        EntityArmorStand entityArmorStand = ((CraftArmorStand)stands.get(0)).getHandle();
        entityArmorStand.setInvisible(true);
        stands.get(0).setGravity(false);
        stands.get(0).teleport(spawn);
        stands.get(0).setCustomNameVisible(false);
        stands.get(0).setHelmet(new ItemStack(material));
    }

    public void remove() {
        stands.forEach(org.bukkit.entity.ArmorStand::remove);
    }

}
