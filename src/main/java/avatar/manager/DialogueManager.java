package avatar.manager;

import avatar.game.dialogue.core.displayable.ChoiceWheel;

import java.util.ArrayList;
import java.util.List;

public class DialogueManager {

    private static int groupID = 0;

    private static List<ChoiceWheel> callbacks = new ArrayList<>();

    public static void add(ChoiceWheel choiceCallback){
        callbacks.add(choiceCallback);
    }

    public static void callCallback(int groupid, String choiceID){
        ChoiceWheel choiceWheel = null;
        for(ChoiceWheel choiceWheel1: callbacks){
            if(choiceWheel1.getGroupID() == groupid){
               if(choiceWheel1.hasID(choiceID)){
                   choiceWheel = choiceWheel1;
                   choiceWheel1.getChoice(choiceID).handle();
               }
            }
        }

        if(choiceWheel != null){
            callbacks.remove(choiceWheel);
        }
    }

    public static void incrementID(){groupID++;}

    public static int getGroupID() {
        return groupID;
    }
}
