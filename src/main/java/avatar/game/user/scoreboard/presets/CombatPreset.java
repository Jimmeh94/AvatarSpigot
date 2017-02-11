package avatar.game.user.scoreboard.presets;

import avatar.game.user.UserPlayer;
import avatar.game.user.stats.Stats;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CombatPreset extends ScoreboardPreset {
    public CombatPreset(UserPlayer owner) {
        super(owner);
    }

    @Override
    public void updateScores() {
        UserPlayer owner = getOwner();
        List<String> strings = new ArrayList<>();

        strings.add(ChatColor.BOLD + "Server Name");
        strings.add("==============");
        strings.add(ChatColor.BLACK.toString());

        strings.add(owner.getPresentArea().getDisplayName());
        strings.add(ChatColor.RED.toString());

        strings.add(ChatColor.GRAY + "Mode: " + ChatColor.RED + "combat");
        strings.add(ChatColor.RED.toString() + ChatColor.RED.toString());

        if(owner.getCombatLogger().isInCombat())
            strings.add(ChatColor.GRAY + "Status: " + ChatColor.RED + "in combat");
        else strings.add(ChatColor.GRAY + "Status: " + ChatColor.GREEN + "out of combat");
        strings.add(ChatColor.RED.toString() + ChatColor.RED.toString() + ChatColor.RED.toString());

        strings.add(ChatColor.GRAY + "Energy: " + ChatColor.AQUA + owner.getStats().getStat(Stats.StatType.CHI).get().getCurrent());

        setScores(strings);
    }
}
