package avatar.game.dialogue.actions;

import avatar.Avatar;
import avatar.game.quest.QuestReference;
import avatar.game.user.UserPlayer;
import org.bukkit.entity.Player;

public class GiveQuest extends DialogueAction {

    public QuestReference questID;

    public GiveQuest(QuestReference id){
        this.questID = id;
    }

    @Override
    public void doWork(Player player) {
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(player).get();

        if(userPlayer.getQuestManager().canTakeQuest(questID))
            userPlayer.getQuestManager().add(questID.getQuest(userPlayer));
    }
}
