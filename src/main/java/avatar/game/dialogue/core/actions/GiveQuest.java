package avatar.game.dialogue.core.actions;

import avatar.Avatar;
import avatar.game.quest.QuestReference;
import org.bukkit.entity.Player;

public class GiveQuest extends DialogueAction {

    public QuestReference questID;

    public GiveQuest(QuestReference id){
        this.questID = id;
    }

    @Override
    public void doWork(Player player) {
        Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getQuestManager().add(questID);
    }
}
