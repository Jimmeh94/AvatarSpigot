package avatar.game.dialogue.core.conditions;

import org.bukkit.entity.Player;

public interface Condition {

    /*
     * A condition that must be met to keep the conversation going, i.e. stay in the range of the character
     */

    /*
     * Making sure that the current action is still valid to perform
     */
    boolean isValid(Player player);

    /*
     * Send an error message if isValid returns false
     */
    void sendErrorMessage(Player player);

}
