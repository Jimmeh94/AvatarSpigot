package avatar.game.quest.builder;

import avatar.game.quest.Checkpoint;
import avatar.game.quest.Quest;
import avatar.game.quest.Reward;
import avatar.game.user.UserPlayer;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class QuestBuilder {

    /*
     * simple way to hard code quest. Use the questBuilder.getCheckpointBuilder to build checkpoints.
     * Then use questBuilder.checkpoints() to lead checkpoints from there
     */

    private CheckpointBuilder checkpointBuilder = new CheckpointBuilder();
    private String title, description;
    private List<Checkpoint> checkpoints;
    private int lvl = 1;
    private String id;
    private Material itemType;
    private Reward reward;
    private UserPlayer owner;

    public QuestBuilder reward(Reward reward){
        this.reward = reward;
        return this;
    }

    public QuestBuilder itemType(Material itemType){
        this.itemType = itemType;
        return this;
    }

    public QuestBuilder name(String name){
        title = name;
        return this;
    }

    public QuestBuilder description(String d){
        description = d;
        return this;
    }

    public QuestBuilder checkpoints(){
        checkpoints = checkpointBuilder.finish();
        return this;
    }

    public QuestBuilder level(int level){
        lvl = level;
        return this;
    }

    public QuestBuilder setID(String id){
        this.id = id;
        return this;
    }

    public Quest build(){
        Quest quest = new Quest(title, description, lvl, id, new ArrayList<>(checkpoints), itemType, reward, owner);
        reset();
        return quest;
    }

    public void reset() {
        title = description = null;
        lvl = 1;
        checkpoints.clear();
    }


    public CheckpointBuilder getCheckpointBuilder() {
        return checkpointBuilder;
    }

    public QuestBuilder owner(UserPlayer userPlayer) {
        owner = userPlayer;
        return this;
    }
}
