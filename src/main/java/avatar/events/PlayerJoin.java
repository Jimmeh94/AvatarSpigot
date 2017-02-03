package avatar.events;

import avatar.game.user.UserPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    //these itemstacks are also in BuildContainer, in case you need to change the items

    private static Location spawn = new Location(Bukkit.getWorlds().get(0), -811.5, 6, 303.5, 90f, 0f);

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        new UserPlayer(event.getPlayer().getUniqueId()).init();
        event.getPlayer().teleport(spawn);
    }

}
