package avatar.game.quest.condition;

import avatar.Avatar;
import avatar.events.custom.EnvironmentEvent;
import avatar.manager.ServerEInteractableManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class InteractEI extends Condition implements Listener {

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private ServerEInteractableManager.ServerEIReference reference;

    public InteractEI(ServerEInteractableManager.ServerEIReference reference) {
        this.reference = reference;
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
        EnvironmentEvent.ServerInteractable.getHandlerList().unregister(this);
    }

    @Override
    public void displayWarningMessage() {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handle(EnvironmentEvent.ServerInteractable event) throws Exception {
        if(event.getEnvironmentInteractable().getReference() == reference){
            valid = true;
            unregisterListener();
        }
    }
}
