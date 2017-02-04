package avatar.events;

import avatar.Avatar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkEvents implements Listener {

    @EventHandler
    public void onLoad(ChunkLoadEvent event){
        Avatar.INSTANCE.getServerEIManager().load(event.getChunk().getX(), event.getChunk().getZ());
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent event){
        Avatar.INSTANCE.getServerEIManager().unload(event.getChunk().getX(), event.getChunk().getZ());
    }

}
