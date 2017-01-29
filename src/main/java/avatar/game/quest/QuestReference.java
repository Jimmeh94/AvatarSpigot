package avatar.game.quest;

import avatar.game.quest.quests.test.TestQuestLocation;
import avatar.game.user.UserPlayer;

import java.util.Optional;

public enum QuestReference {

    TEST("test", new TestQuestLocation());

    private String id;
    private IQuestInitiator clazz;

    QuestReference(String id, IQuestInitiator clazz){
        this.id = id;
        this.clazz = clazz;
    }

    public Quest getQuest(UserPlayer userPlayer){
        return clazz.createLocationTest(userPlayer);
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
