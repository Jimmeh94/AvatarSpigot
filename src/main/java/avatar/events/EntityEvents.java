package avatar.events;

import avatar.Avatar;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntityEvents implements Listener {

    public EntityEvents(){
        for(Entity entity: Bukkit.getWorlds().get(0).getEntities()){
            if(entity instanceof ArmorStand){
                if(!Avatar.INSTANCE.getHologramManager().find(entity.getLocation()).isPresent()){
                    entity.remove();
                }
            } else if(!Avatar.INSTANCE.getEntityManager().find(entity).isPresent()){
                entity.remove();
            }
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        Avatar.INSTANCE.getEntityManager().addEntityToCheck(event.getEntity());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        event.setCancelled(true);
    }

}
