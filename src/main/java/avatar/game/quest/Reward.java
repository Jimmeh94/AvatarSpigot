package avatar.game.quest;

import org.bukkit.entity.Player;

public interface Reward {

    /*
     * Quest rewards
     */

    String getDescription();

    void giveReward(Player player);

}
