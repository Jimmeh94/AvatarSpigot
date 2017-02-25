package avatar.game.quest;

import avatar.game.quest.quests.test.DemoQuest;
import avatar.game.user.UserPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public enum QuestReference {

    DEMO("DemoQuest", DemoQuest.class);

    private String id;
    private Class<? extends Quest> quest;

    QuestReference(String id, Class<? extends Quest> quest)
    {
        this.id = id;
        this.quest = quest;
    }

    public Quest getQuest(UserPlayer userPlayer){
        try {
            return quest.getConstructor(UserPlayer.class).newInstance(userPlayer);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
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

    public static QuestReference find(String id) {
        for(QuestReference reference: QuestReference.values()){
            if(reference.id.equals(id)){
                return reference;
            }
        }
        return null;
    }
}
