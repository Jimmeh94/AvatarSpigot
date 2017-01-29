package avatar.game.quest.condition;

import avatar.util.text.Messager;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Optional;

public class BoundRadius extends Condition{

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private double radius;
    private Location center;

    public BoundRadius(double radius, Location center){
        this.radius = radius;
        this.center = center;
    }

    @Override
    public boolean isValid() {
        return center.distance(getPlayer().getLocation()) <= radius;
    }

    @Override
    protected void unregister() {

    }

    @Override
    public void reset() {
        super.reset();
        getPlayer().teleport(getStartLocation());
    }

    @Override
    public void displayWarningMessage() {
        if(shouldSendWarningMessage()){
            setLastWarningMessage();
            Messager.sendMessage(getPlayer(), ChatColor.GRAY + "You're outside of the quest region! Go back to continue the quest!", Optional.of(Messager.Prefix.ERROR));
        }
    }

}
