package avatar.game.entity.hologram;

import avatar.Avatar;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClientHologram extends Hologram {

    private Location spawnAt;
    private List<Player> displayTo;
    private List<EntityArmorStand> stands;
    private ItemStack mainHand, offHand, helmet, bodyArmor, pants, boots;
    private ItemStack playerHead;
    private boolean placeHeadOnLowestStand = false;
    private HologramInteraction interaction;

    public ClientHologram(Location spawnLocation, List<Player> displayTo, List<String> name, HologramInteraction interaction) {
        super();

        this.spawnAt = spawnLocation;
        this.displayTo = displayTo;
        this.interaction = interaction;
        stands = new ArrayList<>();

        for(int i = 0; i < name.size(); i++){
            stands.add(new EntityArmorStand(((CraftWorld) spawnLocation.getWorld()).getHandle()));
            stands.get(i).setLocation(spawnLocation.getX(), spawnLocation.getY() - (0.25 * i), spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation.getPitch());
            stands.get(i).setInvisible(true);
            stands.get(i).setCustomNameVisible(true);
            stands.get(i).setCustomName(name.get(i));
        }

        Avatar.INSTANCE.getHologramManager().add(this);
    }

    @Override
    public boolean hasLocation(Location location){
        for(EntityArmorStand entityArmorStand: stands){
            Location temp = entityArmorStand.getBukkitEntity().getLocation();
            if(temp.getBlockX() == location.getBlockX() && temp.getBlockZ() == location.getBlockZ()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void remove(){
        for (Player player : this.displayTo) {
            if(player != null && player.isOnline()) {
                for(EntityArmorStand armorStand1 : stands) {
                    PacketPlayOutEntityDestroy destroyArmorStandPacket = new PacketPlayOutEntityDestroy(armorStand1.getId());
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(destroyArmorStandPacket);
                }
            }
        }
        Avatar.INSTANCE.getHologramManager().remove(this);
    }

    public void onInteract(Player player){
        if(this.interaction != null){
            this.interaction.onInteract(player, this);
        }
    }

    public void spawn() {
        for (Player player : this.displayTo) {
            if(player != null && player.isOnline()) {
                for(EntityArmorStand armorStand1 : stands) {
                    PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armorStand1);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
                if(mainHand != null){
                    PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(stands.get(0).getId(), EnumItemSlot.MAINHAND, mainHand);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
                if(offHand != null){
                    PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(stands.get(0).getId(), EnumItemSlot.OFFHAND, offHand);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
                if(playerHead != null){
                    int get = 0;
                    if(placeHeadOnLowestStand)
                        get = stands.size() - 1;
                    PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(stands.get(get).getId(), EnumItemSlot.HEAD, playerHead);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                } else if(helmet != null){
                    PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(stands.get(0).getId(), EnumItemSlot.HEAD, helmet);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
                if(bodyArmor != null){
                    PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(stands.get(0).getId(), EnumItemSlot.CHEST, bodyArmor);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
                if(pants != null){
                    PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(stands.get(0).getId(), EnumItemSlot.LEGS, pants);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
                if(boots != null){
                    PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(stands.get(0).getId(), EnumItemSlot.FEET, boots);
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
            }
        }
    }

    public void setMainHand(ItemStack itemInHand) {
        this.mainHand = itemInHand;
    }

    public void setOffHand(ItemStack offHand) {
        this.offHand = offHand;
    }

    public void setPlaceHeadOnLowestStand(boolean placeHeadOnLowestStand) {
        this.placeHeadOnLowestStand = placeHeadOnLowestStand;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    public void setPants(ItemStack pants) {
        this.pants = pants;
    }

    public void setBodyArmor(ItemStack bodyArmor) {
        this.bodyArmor = bodyArmor;
    }

    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    public void setPlayerHead(ItemStack playerHead) {
        this.playerHead = playerHead;
    }
}
