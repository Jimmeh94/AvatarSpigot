package avatar.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class InventoryPickUp implements Listener {

    @EventHandler
    public void onPickUp(InventoryPickupItemEvent event){
        event.setCancelled(true);
    }

}
