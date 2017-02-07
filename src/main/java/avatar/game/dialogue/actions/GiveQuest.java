package avatar.game.dialogue.actions;

import avatar.game.quest.QuestReference;
import avatar.game.user.UserPlayer;

public class GiveQuest extends DialogueAction {

    public QuestReference questID;

    public GiveQuest(QuestReference id){
        this.questID = id;
    }

    @Override
    public void doWork(UserPlayer userPlayer) {
        if(userPlayer.getQuestManager().canTakeQuest(questID))
            userPlayer.getQuestManager().add(questID.getQuest(userPlayer));
    }
}
