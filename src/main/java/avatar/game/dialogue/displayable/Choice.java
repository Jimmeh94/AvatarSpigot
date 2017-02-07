package avatar.game.dialogue.displayable;

import avatar.Avatar;
import avatar.events.custom.DialogueEvent;
import avatar.game.dialogue.actions.DialogueAction;
import avatar.game.dialogue.conditions.Condition;
import avatar.game.user.UserPlayer;
import avatar.manager.ListenerManager;
import avatar.util.text.JsonMessager;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Choice implements Displayable{

    /*
     * This is a clickable choice that is linked to an action
     * These are stored within the ChoiceWheel
     */

    private List<DialogueAction> actions;
    private List<Condition> conditions;
    private PacketPlayOutChat sentence;
    private Player player;
    private String choiceID;

    public Choice(String text, String hover, String choiceID, Player player, DialogueAction... action){
        this.actions = Arrays.asList(action);
        this.choiceID = choiceID;
        sentence = JsonMessager.getChatPacket(text, JsonMessager.ActionCommand.COMMAND, "/choice " + choiceID, hover);
        this.player = player;
    }

    @Override
    public void display(Player player) {
        JsonMessager.sendMessage(player, sentence);
    }

    public boolean handle() {
        Optional<UserPlayer> temp = Avatar.INSTANCE.getUserManager().findUserPlayer(this.player);
        if(temp.isPresent() && temp.get().getDialogueManager().getCurrentDialogue() != null) {
            if(conditions != null){
                for(Condition condition: conditions){
                    if(!condition.isValid(player)){
                        condition.sendErrorMessage(player);
                        return false;
                    }
                }
            }
            temp.get().getDialogueManager().removeDialogue();

            //if all condition are valid, continue with action
            for (DialogueAction action : this.actions)
                action.doWork(temp.get());

            Bukkit.getServer().getPluginManager().callEvent(new DialogueEvent.ChoiceClicked(ListenerManager.getDefaultCause(), this.choiceID, temp.get()));
            return true;
        }
        return false;
    }

    public String getChoiceID() {
        return choiceID;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
