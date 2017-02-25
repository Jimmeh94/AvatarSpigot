package avatar.game.quest;

import avatar.Avatar;
import avatar.game.quest.condition.*;
import avatar.util.directional.PlayerDirection;
import avatar.util.misc.Items;
import avatar.util.text.Action;
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
    private ICheckpointCompleteAction completeAction;

    public Checkpoint(Player player, Optional<Location> location, String description, ICheckpointCompleteAction checkpointCompleteAction, Condition... conditions){
        this.player = player;
        targetLocation = location;
        this.description = Optional.of(description);
        this.completeAction = checkpointCompleteAction;
        this.conditions = Arrays.asList(conditions);
    }

    public boolean isCompleted(){
        return this.complete;
    }

    public void doCompleteAction(){
        if(completeAction != null){
            completeAction.doAction(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get());
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
                if(condition instanceof BoundArea){
                    ((BoundArea)condition).doLeavingAction();
                }
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

    private int getTrackerDistance() {
        return (int) getTargetLocation().get().distance(player.getLocation());
    }

    public boolean hasCondition(Class<? extends Condition> condition){
        for(Condition c: conditions){
            if(c.getClass().getCanonicalName().equals(condition.getClass().getCanonicalName())){
                return true;
            } else if(c instanceof Fork){
                for(Condition c2: ((Fork)c).getConditions()){
                    if(c2.getClass().getCanonicalName().equals(condition.getCanonicalName())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Condition getCondition(Class<? extends Condition> condition){
        for(Condition c: conditions){
            if(c.getClass().getCanonicalName().equals(condition.getClass().getCanonicalName())){
                return c;
            } else if(c instanceof Fork){
                for(Condition c2: ((Fork)c).getConditions()){
                    if(c2.getClass().getCanonicalName().equals(condition.getCanonicalName())){
                        return c2;
                    }
                }
            }
        }
        return null;
    }

    public void printActionMessage() {
        String send = ChatColor.WHITE + getDescription().get();

        if(hasCondition(ReachArea.class)){
            targetLocation = Optional.of(((ReachArea)getCondition(ReachArea.class)).getTrackerLocation(player.getLocation()));
        } else if(hasCondition(BoundArea.class)){
            BoundArea boundArea = (BoundArea) getCondition(BoundArea.class);
            if(!boundArea.isValid()){
                targetLocation = Optional.of(boundArea.getTrackerLocation(player.getLocation()));
                send = boundArea.getOutofBoundsMessage();
            } else targetLocation = Optional.empty();
        }

        if (targetLocation.isPresent()) {
            int distance = getTrackerDistance();
            send += " " + ChatColor.GOLD + String.valueOf(distance) + " "
                    + PlayerDirection.getDesiredDirection(getPlayer(), getTargetLocation().get());
        }

        Action.send(player, send);
    }

    public void checkItemForQuestItem(Location location, Items items) {
        for(Condition condition: conditions){
            if(condition instanceof ItemInteract){
                ((ItemInteract)condition).handle(location, items);
            }
        }
    }

    public boolean isFork() {
        for(Condition condition: conditions){
            if(condition instanceof Fork){
                return true;
            }
        }
        return false;
    }

    public List<Checkpoint> getForkedCheckpoint() {
        for(Condition condition: conditions){
            if(condition instanceof Fork){
                return ((Fork)condition).getForkedCheckpoint();
            }
        }
        return null;
    }
}
