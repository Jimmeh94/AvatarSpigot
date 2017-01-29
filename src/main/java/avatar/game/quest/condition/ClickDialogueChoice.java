package avatar.game.quest.condition;

import avatar.Avatar;
import avatar.events.custom.DialogueEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClickDialogueChoice extends Condition implements Listener{

    private String choiceID;

    public ClickDialogueChoice(String choiceID) {
        this.choiceID = choiceID;
    }

    @Override
    public void reset() {
        super.reset();
        getPlayer().teleport(getStartLocation());

        unregisterListener();
        setAdditionalStartInfo();
    }

    @Override
    public void setAdditionalStartInfo() {
        Avatar.INSTANCE.getServer().getPluginManager().registerEvents(this, Avatar.INSTANCE);
    }

    @Override
    protected void unregister() {
        DialogueEvent.ChoiceClicked.getHandlerList().unregister(this);
    }

    @EventHandler
    public void handle(DialogueEvent.ChoiceClicked choiceClicked) throws Exception {
        if(choiceClicked.getUserPlayer().getPlayer() == this.getPlayer() && choiceClicked.getChoiceID().equals(this.choiceID)){
            valid = true;
        }
    }
}
