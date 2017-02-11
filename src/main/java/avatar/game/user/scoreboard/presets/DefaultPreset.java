package avatar.game.user.scoreboard.presets;

import avatar.game.user.UserPlayer;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class DefaultPreset extends ScoreboardPreset {

    /*
     * Server name
     * ============
     * Area the player is in
     *
     * Mode
     *
     * Bounty
     *
     * Gold
     *
     * Element Equipped
     */

    public DefaultPreset(UserPlayer statistics){
        super(statistics);
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

        strings.add(ChatColor.GRAY + "Mode: " + ChatColor.GREEN + "passive");
        strings.add(ChatColor.BLACK.toString());

        strings.add("Bounty: 0");
        strings.add(ChatColor.RED.toString() + ChatColor.RED.toString());

        strings.add("Gold: " + owner.getAccount().getBalance());
        strings.add(ChatColor.RED.toString() + ChatColor.RED.toString() + ChatColor.RED.toString());

        strings.add("Element: Fire");
        strings.add(ChatColor.RED.toString() + ChatColor.RED.toString() + ChatColor.RED.toString() + ChatColor.RED.toString());

        strings.add("Chat: " + owner.getChatChannel().getKey());

        setScores(strings);
    }
}
