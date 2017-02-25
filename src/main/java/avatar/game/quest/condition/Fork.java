package avatar.game.quest.condition;

import avatar.game.quest.Checkpoint;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Offers the player a choice of how they want to continue the quest.
 * The list of checkpoints needs to be the rest of the checkpoints for the quest for each particular choice
 * Example: pass in a ClickDialogueChoice condition and a KillEntity condition,
 * the player will then have a choice of resolving the quest peacefully or by violence
 */
public class Fork extends Condition{

    private Map<Condition, List<Checkpoint>> forks;

    public Fork(Player player, Map<Condition, List<Checkpoint>> forks){
        super(player);
        this.forks = forks;
    }

    public Set<Condition> getConditions() {
        return forks.keySet();
    }

    @Override
    public boolean isValid() {
        for(Condition condition: forks.keySet()){
            if(condition.isValid()){
                return true;
            }
        }
        return false;
    }

    public List<Checkpoint> getForkedCheckpoint() {
        for(Condition condition: forks.keySet()){
            if(condition.isValid()){
                return forks.get(condition);
            }
        }
        return null;
    }
}
