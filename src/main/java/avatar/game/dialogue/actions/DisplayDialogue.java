package avatar.game.dialogue.actions;

import avatar.game.user.UserPlayer;

public abstract class DisplayDialogue extends DialogueAction {

    @Override
    public abstract void doWork(UserPlayer userPlayer);
}
