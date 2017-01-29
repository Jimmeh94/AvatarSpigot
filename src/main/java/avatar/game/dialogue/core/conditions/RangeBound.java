package avatar.game.dialogue.core.conditions;

import avatar.util.text.Messager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;

public class RangeBound implements Condition {

    /*
     * Have to be within range to use choicewheel
     */

    private double radius = 5.0;
    private Location center;

    public RangeBound(Location center){
        this.center = center;
    }

    public RangeBound(Location center, double radius){
        this.center = center;
        this.radius = radius;
    }

    @Override
    public boolean isValid(Player player) {
        return center.distance(player.getLocation()) <= radius;
    }

    @Override
    public void sendErrorMessage(Player player) {
        Messager.sendMessage(player, ChatColor.GRAY + "Too far away from the source to perform that action!", Optional.of(Messager.Prefix.ERROR));
    }
}
