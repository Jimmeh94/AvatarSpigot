package avatar.events;

import avatar.Avatar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;

public class EntityEvents implements Listener {

     /* public EntitySpawn(){

        Iterator<Entity> iterator = Bukkit.getWorld("world").getEntities().iterator();
        while(iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.remove();
            iterator.remove();
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if(!Avatar.INSTANCE.getEntityManager().find(event.getEntity().getEntityId()).isPresent()) {
            event.setCancelled(true);
        }
    }*/

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        event.setCancelled(true);
    }

}
