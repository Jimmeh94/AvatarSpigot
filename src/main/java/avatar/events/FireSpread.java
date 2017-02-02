package avatar.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class FireSpread implements Listener {

    @EventHandler
    public void onIgnite(BlockIgniteEvent event){
        event.setCancelled(true);
    }

}
