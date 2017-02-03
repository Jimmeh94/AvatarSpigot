package avatar.game.user.scoreboard.presets;

import avatar.game.user.UserPlayer;
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
        strings.add("In combat!");
        strings.add(ChatColor.RED.toString() + ChatColor.RED.toString());

        setScores(strings);
    }
}
