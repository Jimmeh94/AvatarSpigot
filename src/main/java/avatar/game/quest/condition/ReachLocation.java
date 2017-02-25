package avatar.game.quest.condition;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ReachLocation extends Condition {

    /*
     * Simply a condition to where a player must reach a location
     */

    private Location targetLocation;
    private double completionRadius = 1.5;

    public ReachLocation(Player player, Location location, double radius) {
        super(player);
        targetLocation = location;
        completionRadius = radius;
    }

    @Override
    public boolean isValid() {
        return getPlayer().getLocation().distance(targetLocation) <= completionRadius;
    }
}
