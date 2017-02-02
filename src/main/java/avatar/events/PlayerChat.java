package avatar.events;

import avatar.Avatar;
import avatar.game.user.UserPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        event.setCancelled(true);
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(event.getPlayer()).get();
        userPlayer.getChatChannel().displayMessage(userPlayer.getChatColorTemplate().getPrefix() + userPlayer.getTitle().getDisplay()
                + userPlayer.getChatColorTemplate().getName() + userPlayer.getPlayer().getDisplayName() + ": "
                + userPlayer.getChatColorTemplate().getMessage() + event.getMessage());
    }

}
