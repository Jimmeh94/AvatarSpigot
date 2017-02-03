package avatar.events.custom;

import avatar.Avatar;
import avatar.game.user.UserPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        event.setCancelled(true);

        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(event.getPlayer()).get();

        if(event.getItem() != null){
            userPlayer.getHotbarSetup().handle(userPlayer.getPlayer().getInventory().getHeldItemSlot());
        }
    }

}
