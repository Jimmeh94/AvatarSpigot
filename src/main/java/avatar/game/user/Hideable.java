package avatar.game.user;

import org.bukkit.entity.Player;

/**
 * Use this to stop the spawning packet being sent to a player
 */
public interface Hideable {

    void addViewer(Player player);

    void removeViewer(Player player);

    boolean canSee(Player player);

}
