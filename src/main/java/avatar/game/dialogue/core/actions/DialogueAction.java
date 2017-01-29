package avatar.game.dialogue.core.actions;

import org.bukkit.entity.Player;

public abstract class DialogueAction {

    /*
     * An action linked to a Choice
     */

    public abstract void doWork(Player player);

}
