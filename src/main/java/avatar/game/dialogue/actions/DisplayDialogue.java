package avatar.game.dialogue.actions;

import avatar.Avatar;
import avatar.game.dialogue.Dialogue;
import org.bukkit.entity.Player;

public abstract class DisplayDialogue extends DialogueAction {

    public abstract Dialogue buildToDisplay(Player player);

    @Override
    public void doWork(Player player) {
        Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getDialogueManager().setCurrentDialogue(buildToDisplay(player)).getDialogueManager().startDialogue();
    }
}
