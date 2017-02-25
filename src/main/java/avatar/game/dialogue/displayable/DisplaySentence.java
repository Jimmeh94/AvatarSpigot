package avatar.game.dialogue.displayable;

import avatar.game.dialogue.actions.DialogueAction;
import avatar.game.user.UserPlayer;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;

import java.util.Optional;

public class DisplaySentence extends DialogueAction {

    private String display;

    public DisplaySentence(String s) {
        this.display = s;
    }

    @Override
    public void doWork(UserPlayer userPlayer) {
        Messager.sendMessage(userPlayer.getPlayer(), ChatColor.GRAY + display, Optional.<Messager.Prefix>empty());
        Messager.sendBlankLine(userPlayer.getPlayer());
    }
}
