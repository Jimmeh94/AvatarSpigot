package avatar.manager;

import avatar.game.dialogue.displayable.ChoiceWheel;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DialogueManager {

    private static int groupID = 0;

    private static List<ChoiceWheel> callbacks = new CopyOnWriteArrayList<>();

    public static void add(ChoiceWheel choiceCallback){
        callbacks.add(choiceCallback);
    }

    public static void callCallback(int groupid, String choiceID){
        Iterator<ChoiceWheel> iterator = callbacks.iterator();
        while(iterator.hasNext()){
            ChoiceWheel choiceWheel = iterator.next();
            if(choiceWheel.getGroupID() == groupid){
               if(choiceWheel.hasID(choiceID)){
                   if(choiceWheel.getChoice(choiceID).handle()){
                       callbacks.remove(choiceWheel);
                   }
               }
            }
        }
    }

    public static void incrementID(){groupID++;}

    public static int getGroupID() {
        return groupID;
    }
}
