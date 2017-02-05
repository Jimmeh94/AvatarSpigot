package avatar.util.misc;

import avatar.game.dialogue.PlayerDialogueManager;
import avatar.game.quest.PlayerQuestManager;
import avatar.game.quest.QuestReference;
import avatar.game.user.UserPlayer;

public class Qualifier {

    private When checkWhen;
    public What checkWhat;
    private String id;

    public Qualifier(What checkWhat, When checkWhen, String id){
        this.checkWhen = checkWhen;
        this.checkWhat = checkWhat;
        this.id = id;
    }

    public boolean valid(UserPlayer userPlayer){
        if(checkWhat == What.QUEST){
            QuestReference reference = QuestReference.find(id);
            PlayerQuestManager questManager = userPlayer.getQuestManager();
            if(checkWhen == When.BEFORE){
                return !questManager.has(reference) && !questManager.hasCompleted(reference);
            } else if(checkWhen == When.DURING){
                return questManager.has(reference) && !questManager.hasCompleted(reference);
            } else {
                return !questManager.has(reference) && questManager.hasCompleted(reference);
            }
        } else {
            PlayerDialogueManager dialogueManager = userPlayer.getDialogueManager();
            if(checkWhen == When.BEFORE){
                return !dialogueManager.hasCompleted(id);
            } else if(checkWhen == When.AFTER){
                return dialogueManager.hasCompleted(id);
            }
        }
        return false;
    }

    public enum When{
        BEFORE,
        DURING,
        AFTER
    }

    public enum What{
        QUEST,
        DIALOGUE
    }

}
