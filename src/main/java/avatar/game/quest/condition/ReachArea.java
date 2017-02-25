package avatar.game.quest.condition;

import avatar.Avatar;
import avatar.game.area.Area;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class ReachArea extends Condition{

    /*
     * Simply a condition to where a player must reach a location
     */

    private Area targetArea;

    public ReachArea(Player player, Area area) {
        super(player);
        this.targetArea = area;
    }

    @EventHandler
    public boolean isValid() {
        if(Avatar.INSTANCE.getUserManager().findUserPlayer(getPlayer()).get().getPresentArea() == targetArea){
            valid = true;
        }
        return valid;
    }

    public Location getTrackerLocation(Location player) {
        Location give = null;

        for(Location location: targetArea.getShape().getOutline()){
            if(give == null){
                give = location.clone();
            } else {
                if(player.distance(give) > player.distance(location)){
                    give = location.clone();
                }
            }
        }

        return give;
    }
}
