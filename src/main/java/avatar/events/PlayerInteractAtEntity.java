package avatar.events;

import avatar.Avatar;
import avatar.game.entity.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.Optional;

public class PlayerInteractAtEntity implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event){
        event.setCancelled(true);

        Optional<NPC> npc = Avatar.INSTANCE.getEntityManager().find(event.getRightClicked().getEntityId());
        if(npc.isPresent()){
            npc.get().onInteract(event.getPlayer());
        }
    }

}
