package avatar.game.entity.npc.nms;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityTypes;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import org.bukkit.entity.EntityType;

public enum CustomEntities {

    //CUSTOM_ZOMBIE("Custom Zombie", 54, EntityType.ZOMBIE, EntityZombie.class, CustomZombie.class);
    ;

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends Entity> nmsClass;
    private Class<? extends Entity> customClass;
    private MinecraftKey key;
    private MinecraftKey oldKey;

    @SuppressWarnings("unchecked")
    private CustomEntities(String name, int id, EntityType entityType, Class<? extends Entity> nmsClass, Class<? extends Entity> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
        this.key = new MinecraftKey(name);
        this.oldKey = EntityTypes.b.b(nmsClass);
    }

    public static void registerEntities() {
        for (CustomEntities ce : CustomEntities.values()){
            ce.register();
        }
    }
    public static void unregisterEntities() {
        for (CustomEntities ce : CustomEntities.values()){
            ce.unregister();
        }
    }

    @SuppressWarnings("unchecked")
    private void register() {
        EntityTypes.d.add(key);
        EntityTypes.b.a(id, key, customClass);
    }

    @SuppressWarnings("unchecked")
    private void unregister() {
        EntityTypes.d.remove(key);
        EntityTypes.b.a(id, oldKey, nmsClass);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<?> getCustomClass() {
        return customClass;
    }
}
