package avatar.game.dialogue;

import avatar.game.dialogue.test.BrothersDialogue;
import avatar.game.dialogue.test.DemoDialogue;
import avatar.game.dialogue.test.ReturnGlassesDialogue;
import avatar.game.user.UserPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public enum DialogueReference {

    DEMO("test", DemoDialogue.class),
    BROTHERS("brothers", BrothersDialogue.class),
    RETURN_GLASSES("returnGlasses", ReturnGlassesDialogue.class);

    private String id;
    private Class<? extends Dialogue> clazz;

    DialogueReference(String id, Class<? extends Dialogue> clazz){
        this.id = id;
        this.clazz = clazz;
    }

    public Dialogue getDialogue(UserPlayer userPlayer){
        try {
            return clazz.getConstructor(UserPlayer.class).newInstance(userPlayer);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Optional<DialogueReference> getReference(String id){
        for(DialogueReference reference: DialogueReference.values()){
            if(reference.getID().equals(id)){
                return Optional.of(reference);
            }
        }
        return Optional.empty();
    }

    public String getID() {
        return id;
    }
}
