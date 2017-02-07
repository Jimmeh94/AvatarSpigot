package avatar.game.dialogue;

import avatar.Avatar;
import avatar.game.dialogue.displayable.Choice;
import avatar.game.dialogue.displayable.Displayable;
import avatar.game.user.UserPlayer;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Dialogue {

    /*
     * Container for whole conversation, both sentences and actions
     * Each dialogue should only contain up to one choice
     * If an option in that Choice continues the conversation, that next part of the conversation
     * should be another Dialogue, using the DisplayDialogue DialogueAction as part of that particular Choice
     */

    protected List<Displayable> initialDialogue = new ArrayList<>();
    private UserPlayer player;
    private String dialogueID;
    private boolean used = false;
    private Data data;

    protected abstract void setupInitialDialogue();

    public Dialogue(String id, UserPlayer player, Data data){
        dialogueID = String.valueOf(id);
        this.player = player;
        this.data = data;

        setupInitialDialogue();
    }

    public UserPlayer getPlayer() {
        return player;
    }

    public String getDialogueID() {
        return dialogueID;
    }


    public void handleChoice(String id){
        int choice = Integer.valueOf(id.split("\\.")[1]);
        if(initialDialogue.size() > choice){
            if(initialDialogue.get(choice) instanceof Choice){
                ((Choice)initialDialogue.get(choice)).handle();
            }
        }
    }

    protected String getChoiceID(){
        return getDialogueID() + "." + initialDialogue.size();
    }

    /**
     * Don't directly call this to display a player's dialogue. Use UserPlayer#dialogueManager().startDialogue
     */
    public void displayNext(){
        Messager.sendMessage(player.getPlayer(), ChatColor.GRAY + "=================================== ", Optional.empty());
        for(Displayable displayable: initialDialogue){
            displayable.display(player.getPlayer());
        }
        Messager.sendMessage(player.getPlayer(), ChatColor.GRAY + "=================================== ", Optional.empty());

        if(data == Data.UNIQUE){
            Avatar.INSTANCE.getUserManager().findUserPlayer(player.getPlayer()).get().getDialogueManager().completedDialogue(dialogueID);
        }
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    public enum Data{
        //Unique can't be repeated

        REPEATABLE,
        UNIQUE
    }
}
