package avatar.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        event.setCancelled(true);
    }

}
