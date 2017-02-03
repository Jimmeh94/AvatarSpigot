package avatar.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

public class InventoryMove implements Listener {

    @EventHandler
    public void onMove(InventoryMoveItemEvent event){
        event.setCancelled(true);
    }

}
