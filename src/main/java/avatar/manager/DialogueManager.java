package avatar.manager;

import avatar.game.dialogue.core.displayable.ChoiceCallback;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DialogueManager {

    private static int groupID = 0;

    private static Map<Integer, ChoiceCallback> callbacks = new HashMap<>();

    public static void add(ChoiceCallback choiceCallback){
        callbacks.put(groupID, choiceCallback);
    }

    public static void callCallback(int groupid, String choiceID){
        boolean called = false;
        int id = -1;
        Iterator<Map.Entry<Integer, ChoiceCallback>> iterator = callbacks.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, ChoiceCallback> entry = iterator.next();
            if(groupid == entry.getKey() && entry.getValue().getChoiceID().equals(choiceID)){
                if(entry.getValue().handle()){
                    called = true;
                    id = entry.getKey();
                }
            }
        }
        if(called){
            removeAll(id);
        }
    }

    public static void incrementID(){groupID++;}

    private static void removeAll(int id){
        Iterator<Map.Entry<Integer, ChoiceCallback>> iterator = callbacks.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, ChoiceCallback> entry = iterator.next();
            if(entry.getKey() == id){
                iterator.remove();
            }
        }
    }

    public static int getGroupID() {
        return groupID;
    }
}
