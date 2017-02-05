package avatar.game.dialogue;

import avatar.Avatar;
import avatar.game.dialogue.displayable.ChoiceWheel;
import avatar.game.dialogue.displayable.Displayable;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dialogue {

    /*
     * Container for whole conversation, both sentences and actions
     * Each dialogue should only contain up to one choice
     * If an option in that Choice continues the conversation, that next part of the conversation
     * should be another Dialogue, using the DisplayDialogue DialogueAction as part of that particular Choice
     */

    private List<Displayable> dialogue = new ArrayList<>();
    private Player player;
    private String dialogueID;
    private boolean used = false;
    private Data data;

    public Dialogue(List<Displayable> displayables, String string, Player player, Data data){
        this.dialogue = displayables;
        dialogueID = String.valueOf(string);
        this.player = player;
        this.data = data;
    }

    public Player getPlayer() {
        return player;
    }

    public String getDialogueID() {
        return dialogueID;
    }

    public boolean hasChoiceID(String id){
        for(Displayable displayable: dialogue){
            if(displayable instanceof ChoiceWheel && ((ChoiceWheel) displayable).hasID(id)){
                return true;
            }
        }
        return false;
    }

    /**
     * Don't directly call this to display a player's dialogue. Use UserPlayer#dialogueManager().startDialogue
     */
    public void displayNext(){
        Messager.sendMessage(player, ChatColor.GRAY + "=================================== ", Optional.empty());
        for(Displayable displayable: dialogue){
            displayable.display(player);
        }
        Messager.sendMessage(player, ChatColor.GRAY + "=================================== ", Optional.empty());

        if(data == Data.UNIQUE){
            Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getDialogueManager().completedDialogue(dialogueID);
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
