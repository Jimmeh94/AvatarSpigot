package avatar.events;

import avatar.Avatar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.setQuitMessage(null);
        Avatar.INSTANCE.getUserManager().findUserPlayer(event.getPlayer()).get().cleanUp();
    }

}
