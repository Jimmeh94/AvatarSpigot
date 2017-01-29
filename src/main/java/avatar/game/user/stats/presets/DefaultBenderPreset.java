package avatar.game.user.stats.presets;

import avatar.game.user.User;
import avatar.game.user.stats.IStatsPreset;
import avatar.game.user.stats.Stats;

import java.util.ArrayList;
import java.util.List;

public class DefaultBenderPreset implements IStatsPreset {

    @Override
    public List<Stats.Stat> loadStats(User owner) {
        List<Stats.Stat> stats = new ArrayList<>();

        stats.add(new Stats.Stat(Stats.StatType.HEALTH, owner, 100));
        stats.add(new Stats.Stat(Stats.StatType.CHI, owner, 100));

        return stats;
    }
}
