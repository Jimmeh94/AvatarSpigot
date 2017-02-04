package avatar.game.quest.condition;

import avatar.Avatar;
import avatar.events.custom.AreaEvent;
import avatar.game.area.Area;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReachArea extends Condition implements Listener{

    /*
     * Simply a condition to where a player must reach a location
     */

    private Area targetArea;

    public ReachArea(Area area) {
        this.targetArea = area;
    }

    @Override
    public void reset(){
        super.reset();

        unregisterListener();
        setAdditionalStartInfo();
    }

    @Override
    public void setAdditionalStartInfo() {
        Avatar.INSTANCE.getServer().getPluginManager().registerEvents(this, Avatar.INSTANCE);
    }

    @Override
     protected void unregister() {
        AreaEvent.Enter.getHandlerList().unregister(this);
    }

    @EventHandler
    public void handle(AreaEvent.Enter event) throws Exception {
        if(event.getArea() == this.targetArea){
            if(event.getUser().isPlayer()){
                if(event.getUser().getUUID().equals(this.getPlayer().getUniqueId())){
                    valid = true;
                    unregisterListener();
                }
            }
        }
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
