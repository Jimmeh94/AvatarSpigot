package avatar.game.user.stats;


import avatar.game.user.User;

import java.util.List;


public interface IStatsPreset {

    /**
     * Returns a list of default stats.
     * Allows for multiple "presets" based on the User
     * @return
     */
    List<Stats.Stat> loadStats(User owner);

}
