package avatar.events;

import avatar.game.user.UserPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    //these itemstacks are also in BuildContainer, in case you need to change the items

    private static Location spawn = new Location(Bukkit.getWorld("world"), 1457.5,5,-491.5);

    {
        spawn.setYaw(134F);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        new UserPlayer(event.getPlayer().getUniqueId()).init();
    }

}
