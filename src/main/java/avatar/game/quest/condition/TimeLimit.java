package avatar.game.quest.condition;

import avatar.util.text.Messager;
import org.bukkit.ChatColor;

import java.util.Optional;

public class TimeLimit extends Condition{

    /*
     * Checkpoint must be completed within the time limit
     */

    private Long whenStarted;
    private int seconds;

    public TimeLimit(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public boolean isValid() {
        if(whenStarted != null){
            if((System.currentTimeMillis() - whenStarted)/1000 >= seconds)
                return false;
            else return true;
        } else {
            whenStarted = System.currentTimeMillis();
            return true;
        }
    }

    @Override
     protected void unregister() {
    }

    @Override
    public void reset() {
        super.reset();

        getPlayer().teleport(getStartLocation());
        whenStarted = null;
    }

    @Override
    public void displayWarningMessage() {
        if(shouldSendWarningMessage()) {
            setLastWarningMessage();
            Messager.sendMessage(getPlayer(), ChatColor.GRAY + "Time limit expired! Checkpoint reloaded!", Optional.of(Messager.Prefix.ERROR));
        }
    }
}
