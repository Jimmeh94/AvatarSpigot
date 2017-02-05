package avatar.game.dialogue;

import avatar.game.dialogue.test.DemoDialogue;
import org.bukkit.entity.Player;

import java.util.Optional;

public enum DialogueReference {

    DEMO("test", new DemoDialogue());

    private String id;
    private IDialogueInitiator clazz;

    DialogueReference(String id, IDialogueInitiator clazz){
        this.id = id;
        this.clazz = clazz;
    }

    public Dialogue getDialogue(Player player){
        return clazz.build(player);
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
