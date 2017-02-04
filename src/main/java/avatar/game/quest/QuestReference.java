package avatar.game.quest;

import avatar.game.quest.quests.test.DemoQuest;
import avatar.game.user.UserPlayer;

import java.util.Optional;

public enum QuestReference {

    DEMO("demo", new DemoQuest());

    private String id;
    private IQuestInitiator initiator;

    QuestReference(String id, IQuestInitiator initiator)
    {
        this.id = id;
        this.initiator = initiator;
    }

    public Quest getQuest(UserPlayer userPlayer){
        return initiator.getQuest(userPlayer);
    }

    public static Optional<QuestReference> getReference(String id){
        for(QuestReference reference: QuestReference.values()){
            if(reference.getID().equals(id)){
                return Optional.of(reference);
            }
        }
        return Optional.empty();
    }

    public String getID() {
        return id;
    }
}
