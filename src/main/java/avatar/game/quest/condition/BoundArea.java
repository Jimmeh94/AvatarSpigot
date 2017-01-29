package avatar.game.quest.condition;

import avatar.Avatar;
import avatar.events.custom.AreaEvent;
import avatar.game.area.Area;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class BoundArea extends Condition implements Listener{

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
    public void setAdditionalStartInfo() {
        Avatar.INSTANCE.getServer().getPluginManager().registerEvents(this, Avatar.INSTANCE);
    }

    @Override
    protected void unregister() {
        AreaEvent.Exit.getHandlerList().unregister(this);
    }

    @Override
    public void displayWarningMessage() {
        if(shouldSendWarningMessage()){
            setLastWarningMessage();
            Messager.sendMessage(getPlayer(), ChatColor.GRAY + "You're outside of the quest region! Go back to " + bound.getDisplayName() + " continue the quest!", Optional.of(Messager.Prefix.ERROR));
        }
    }

    @EventHandler
    public void handle(AreaEvent.Exit exit) throws Exception {
        if(exit.getArea() == bound){
            valid = false;
        }
    }
}
