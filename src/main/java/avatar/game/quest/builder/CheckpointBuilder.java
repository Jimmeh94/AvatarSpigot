package avatar.game.quest.builder;

import avatar.game.quest.Checkpoint;
import avatar.game.quest.ICheckpointCompleteAction;
import avatar.game.quest.condition.Condition;
import avatar.game.user.UserPlayer;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckpointBuilder {

    /*
     * used in conjunction with the questBuilder. Accessible there. add checkpoint
     * params then use .build() to build a check point and continue to make more. Once you're finished,
     * use questBuilder.checkpoints to load checkpoints from here into quest builder.
     * This SHOULD NOT be used on its own. It is accessible through the quest builder.
     * finish() should not be manually called - only to be called by the questbuilder
     */

    private Optional<Location> targetLocation = Optional.empty();
    private Optional<String> description = Optional.empty();
    private List<Condition> conditions = new ArrayList<>();
    private List<Checkpoint> checkpoints = new ArrayList<>();
    private ICheckpointCompleteAction completeAction;
    private UserPlayer userPlayer;

    public CheckpointBuilder(UserPlayer userPlayer) {
        this.userPlayer = userPlayer;
    }

    public CheckpointBuilder completeAction(ICheckpointCompleteAction completeAction){
        this.completeAction = completeAction;
        return this;
    }

    public List<Checkpoint> finish(){
        List<Checkpoint> temp = new ArrayList<>(checkpoints);
        checkpoints.clear();
        return temp;
    }

    public CheckpointBuilder description(String string){
        description = Optional.of(string);
        return this;
    }

    public CheckpointBuilder targetLocation(Optional<Location> t){
        targetLocation = t;
        return this;
    }

    public CheckpointBuilder condition(Condition condition){
        conditions.add(condition);
        return this;
    }

    public CheckpointBuilder buildCheckpoint(){
        if(description.isPresent()){
            checkpoints.add(new Checkpoint(userPlayer.getPlayer(), targetLocation, description.get(), completeAction, conditions.toArray(new Condition[]{})));
            reset();
        }
        return this;
    }

    private void reset(){
        targetLocation = Optional.empty();
        description = Optional.empty();
        conditions.clear();
    }

}
