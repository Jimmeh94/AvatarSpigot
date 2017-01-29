package avatar.game.dialogue.core;

import avatar.game.dialogue.core.displayable.Displayable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DialogueBuilder {

    /*
     * Use to build a Dialogue
     * Must have a StringID
     * Use the DisplayableBuilder to build sentences and choices, then loadDialogue
     */

    private List<Displayable> dialogue = new ArrayList<>();
    private String stringID = null;

    public DialogueBuilder stringID(String stringID){this.stringID = stringID; return this;}

    public DialogueBuilder addDisplayable(Displayable displayable){
        dialogue.add(displayable);
        return this;
    }

    public Dialogue build(Player player){
        Dialogue give = new Dialogue(new ArrayList<>(dialogue), new String(stringID), player);
        reset();
        return give;
    }

    private void reset(){
        dialogue.clear();
        stringID = null;
    }

    public String getStringID() {
        return stringID;
    }
}
