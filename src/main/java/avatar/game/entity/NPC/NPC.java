package avatar.game.entity.npc;

import avatar.game.user.User;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface NPC {

    Entity getEntity();

    void onInteraction(Player player);

    Location getLocation();

    void remove();

    User getUser();

}
