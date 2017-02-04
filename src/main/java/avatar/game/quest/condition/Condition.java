package avatar.game.quest.condition;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Condition {

    /*
     * Conditions have to do with the environment in which the quest checkpoint must be completed in
     * The Check enum describes when the condition should be checked for validity.
     * These are essentially the goals of each checkpoint
     */

    private Player player;
    private Long lastWarningMessage;
    private Location startLocation;
    protected boolean valid, displayedWarning = false;

    /**
     * Called every game timer iteration
     * Do logic here if needed to
     * @return
     */
    public boolean isValid(){return valid;}

    /*
     * In case there's any additional info the condition will need once it becomes active
     */
    public void setAdditionalStartInfo(){}

    protected abstract void unregister();

    /*
     * Reset the current condition
     * If boolean is true, can/should teleport player back to where they started the checkpoint at
     */
    public void reset(){
        valid = false;
        unregisterListener();
    }

    /*
     * Warning message should be sent to the player if they are being reset
     */
    public void displayWarningMessage(){
        displayedWarning = true;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Condition(){}

    public void setStartingInfo() {
        startLocation = getPlayer().getLocation().clone();
        setAdditionalStartInfo();
    }

    protected Location getStartLocation(){return startLocation;}

    public Player getPlayer() {
        return player;
    }

    protected void setLastWarningMessage(){lastWarningMessage = System.currentTimeMillis();}

    /*
     * Will only send warning message every 10 seconds
     */
    protected boolean shouldSendWarningMessage(){
        if(lastWarningMessage == null)
            return true;
        return ((System.currentTimeMillis() - lastWarningMessage)/1000 >= 10);
    }

    public void unregisterListener() {
        if(this instanceof Listener)
            unregister();
    }
}
