package avatar.game.quest.condition;

import avatar.events.custom.DialogueEvent;
import org.bukkit.entity.Player;

public class ClickDialogueChoice extends Condition{

    private String choiceID;

    public ClickDialogueChoice(Player player, String choiceID) {
        super(player);
        this.choiceID = choiceID;
    }

    @Override
    public void reset() {
        super.reset();

        getPlayer().teleport(getStartLocation());
        setAdditionalStartInfo();
    }

    public void handle(DialogueEvent.ChoiceClicked choiceClicked) throws Exception {
        if(choiceClicked.getUserPlayer().getPlayer() == this.getPlayer() && choiceClicked.getChoiceID().equals(this.choiceID)){
            valid = true;
        }
    }
}
