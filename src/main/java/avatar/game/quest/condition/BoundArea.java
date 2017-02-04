package avatar.game.quest.condition;

import avatar.game.area.Area;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class BoundArea extends Condition{

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private Area bound;

    public BoundArea(Area bound){
        this.bound = bound;
    }

    @Override
    public void reset() {
        super.reset();
        getPlayer().teleport(getStartLocation());

        unregisterListener();
        setAdditionalStartInfo();
    }

    @Override
    protected void unregister() {

    }


    @Override
    public void displayWarningMessage() {
        //May not have a need for this since the tracker is updated with the same thing
        /*if(displayedWarning){
            return;
        }

        if(shouldSendWarningMessage()){
            setLastWarningMessage();
            Messager.sendMessage(getPlayer(), ChatColor.GRAY + "You're outside of the quest region! Go back to " + bound.getDisplayName() + " continue the quest!", Optional.of(Messager.Prefix.ERROR));
        }
        super.displayWarningMessage();*/
    }

    @Override
    public boolean isValid(){
        if(bound.contains(getPlayer().getLocation())){
            //displayedWarning = false;
            return true;
        } else return false;
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
}
