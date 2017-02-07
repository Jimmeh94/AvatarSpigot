package avatar.game.dialogue.actions;

import avatar.game.user.UserPlayer;

public abstract class DialogueAction {

    /*
     * An action linked to a Choice
     */

    public abstract void doWork(UserPlayer userPlayer);

}
