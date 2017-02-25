package avatar.game.quest.condition;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.user.UserPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BoundArea extends Condition{

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private Area bound;
    private LeaveAreaAction leaveAreaAction;
    private EnterAreaAction enterAreaAction;
    /**
     * Means the player has left the quest area, meaning that we can do enterAreaAction
     * once they return
     */
    private boolean leftArea = false;

    public BoundArea(Player player, Area bound){
        this(player, bound, null);
    }

    public BoundArea(Player player, Area bound, LeaveAreaAction leaveAreaAction){
        this(player, bound, leaveAreaAction, null);
    }

    public BoundArea(Player player, Area bound, LeaveAreaAction leaveAreaAction, EnterAreaAction enterAreaAction){
        super(player);
        this.bound = bound;
        this.leaveAreaAction = leaveAreaAction;
        this.enterAreaAction = enterAreaAction;
    }

    @Override
    public void reset() {
        super.reset();
        getPlayer().teleport(getStartLocation());
    }

    @Override
    public boolean isValid(){
        if(bound.contains(getPlayer().getLocation())){
            if(leftArea){
                leftArea = false;
                if(enterAreaAction != null){
                    enterAreaAction.doAction(Avatar.INSTANCE.getUserManager().findUserPlayer(getPlayer()).get(), bound);
                }
            }
            return true;
        } else{
            leftArea = true;
            return false;
        }
    }

    public String getOutofBoundsMessage() {
        return ChatColor.WHITE + "Return to " + bound.getDisplayName() + " to continue this quest";
    }

    public Location getTrackerLocation(Location player) {
        Location give = null;

        for(Location location: bound.getShape().getOutline()){
            if(give == null){
                give = location.clone();
            } else {
                if(player.distance(location) < player.distance(give)){
                    give = location.clone();
                }
            }
        }

        return give;
    }

    public void doLeavingAction(){
        if(leaveAreaAction != null){
            leaveAreaAction.doAction(Avatar.INSTANCE.getUserManager().findUserPlayer(getPlayer()).get(), bound);
        }
    }

    public interface LeaveAreaAction{

        //Use for things like removing a player from an instance
        void doAction(UserPlayer userPlayer, Area area);
    }

    public interface EnterAreaAction{

        //Use for when a player re-enters the quest area
        void doAction(UserPlayer userPlayer, Area area);
    }
}
