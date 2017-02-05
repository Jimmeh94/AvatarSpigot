package avatar.game.dialogue.displayable;

import avatar.game.dialogue.conditions.Condition;
import avatar.manager.DialogueManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceWheel implements Displayable {

    /*
     * Container for holding choices
     */

    private List<Choice> choices = new ArrayList<>();
    private List<Condition> conditions;
    private int groupID;

    public ChoiceWheel(Choice... choices){
        this.choices = Arrays.asList(choices);
        this.groupID = DialogueManager.getGroupID();
        DialogueManager.add(this);
        DialogueManager.incrementID();
    }

    public ChoiceWheel addConditions(List<Condition> condition){
        this.conditions = condition;
        for(Choice choice: choices){
            choice.setConditions(condition);
        }
        return this;
    }

    public List<Condition> getCondition() {
        return conditions;
    }

    private List<Choice> getChoices(){return choices;}

    @Override
    public void display(Player player) {
        for(Choice choice: choices){
            choice.display(player);
        }
    }

    public Choice getChoice(String id){
        for(Choice choice: choices){
            if(choice.getId().equals(id))
                return choice;
        }
        return null;

    }

    public boolean hasID(String id) {
        for(Choice choice: choices){
            if(choice.getId().equals(id))
                return true;
        }
        return false;
    }

    public int getGroupID() {
        return groupID;
    }
}
