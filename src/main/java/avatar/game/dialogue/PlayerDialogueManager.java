package avatar.game.dialogue;

import avatar.events.custom.DialogueEvent;
import avatar.game.user.UserPlayer;
import avatar.manager.ListenerManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class PlayerDialogueManager {

    private List<String> completed = new ArrayList<>();
    private Dialogue currentDialogue;
    private UserPlayer owner;

    public PlayerDialogueManager(UserPlayer userPlayer) {
        this.owner = userPlayer;
    }

    public void removeDialogue() {
        currentDialogue = null;
    }

    public void startDialogue() {
        currentDialogue.displayNext();

        Bukkit.getPluginManager().callEvent(new DialogueEvent.Displayed(ListenerManager.getDefaultCause(), owner));
    }

    public void giveDialogue(DialogueReference reference){
        setCurrentDialogue(reference.getDialogue(getOwner()));
        startDialogue();
    }

    public UserPlayer setCurrentDialogue(Dialogue currentDialogue) {
        this.currentDialogue = currentDialogue;
        return owner;
    }

    public UserPlayer getOwner() {
        return owner;
    }

    public Dialogue getCurrentDialogue() {
        return currentDialogue;
    }

    public void completedDialogue(String id){
        if(!completed.contains(id)){
            completed.add(id);
        }
    }

    public boolean hasCompleted(String id){
        return completed.contains(id);
    }

    public void handleChoice(String arg) {
        if(currentDialogue != null){
            currentDialogue.handleChoice(arg);
        }
    }
}
