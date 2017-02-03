package avatar.events;

import org.bukkit.event.Listener;

public class EntitySpawn implements Listener {

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

}
