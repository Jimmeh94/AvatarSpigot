package avatar.game.quest;

import avatar.game.quest.condition.Condition;
import avatar.game.quest.condition.ReachArea;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Checkpoint {

    /*
     * Quests are made of checkpoints. Checkpoints contain condition that must be met to be completed
     */

    private Player player;
    private Optional<Location> targetLocation = Optional.empty();
    private Optional<String> description = Optional.empty();
    private List<Condition> conditions;
    private boolean complete = false;

    public boolean isCompleted(){
        return this.complete;
    }

    public void deactivate(){
        for(Condition condition: conditions){
            condition.unregisterListener();
        }
    }

    /*
     * Reset progress used for when a certain condition is invalidated. For example, we could use this as a
     * tutorial area with a BoundRadius condition. If the player leaves the tutorial area, they would
     * be teleported back and the checkpoint would restart
     */
    private void resetProgress(){
        for(Condition condition: conditions){
            condition.reset();
        }
    }

    public Checkpoint(Location location, String description, Condition... conditions){
        if(location != null)
            targetLocation = Optional.of(location);
        if(description != null)
            this.description = Optional.of(description);
        if(conditions != null)
            this.conditions = Arrays.asList(conditions);
    }

    /*
     * Just called whenever the checkpoint is initiated/reset. Gives them an idea of what to do
     */
    public void start(){
        for(Condition condition: conditions){
            condition.setStartingInfo();
        }
    }

    /*
     * Called whenever all condition are met and the checkpoint is completed
     */
    public void printCompletionMsg() {
        Messager.sendMessage(player, ChatColor.GRAY + "Checkpoint reached!", Optional.of(Messager.Prefix.SUCCESS));
    }

    /*
     * Quest timer will check this function, making sure all condition and inherited class checks are complete
     */
    public boolean isComplete(){
        if(conditionsMet()){
            this.complete = true;
        }
        return complete;
    }

    /*
     * Makes sure all checkpoint condition are validated before completing
     */
    private boolean conditionsMet(){
        boolean valid = true;
        for(Condition condition: conditions){
            if(!condition.isValid()){
                condition.displayWarningMessage();
                valid = false;
            }
        }

        if(!valid){
            //resetProgress();
        }
        return valid;
    }

    public Optional<Location> getTargetLocation() {
        return targetLocation;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Player getPlayer() {

        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        for(Condition condition: conditions)
            condition.setPlayer(player);
    }

    public int getTrackerDistance() {
        int distance = (int) getTargetLocation().get().distance(player.getLocation());

        for(Condition condition: conditions){
            if(condition instanceof ReachArea){
                return ((ReachArea)condition).getTrackerDistance(distance);
            }
        }
        return distance;
    }
}
