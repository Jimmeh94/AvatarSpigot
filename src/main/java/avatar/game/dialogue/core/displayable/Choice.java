package avatar.game.dialogue.core.displayable;

import avatar.Avatar;
import avatar.event.custom.DialogueEvent;
import avatar.game.dialogue.core.actions.DialogueAction;
import avatar.game.dialogue.core.conditions.Condition;
import avatar.game.user.UserPlayer;
import avatar.util.text.AltCodes;
import avatar.util.text.Messager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.bukkit.entity.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Choice implements Consumer<CommandSource>{

    /*
     * This is a clickable choice that is linked to an action
     * These are stored within the ChoiceWheel
     */

    private List<DialogueAction> actions;
    private List<Condition> conditions;
    private String sentence;
    private Player player;
    private String id;
    private Optional<String> hover = Optional.empty();

    public Choice(String text, String hover, String id, Player player, DialogueAction... action){
        this.sentence = text;
        this.actions = Arrays.asList(action);
        if(hover != null){
            this.hover = Optional.of(hover);
        }
        this.id = id;

        if(this.hover.isPresent()){
            sentence = Text.builder().append(Text.of(TextColors.GREEN, TextStyles.BOLD, AltCodes.ARROW_RIGHT.getSign() + " "), sentence)
                    .onClick(TextActions.executeCallback(this)).onHover(TextActions.showText(this.hover.get())).build();
        } else {
            sentence = Text.builder().append(Text.of(TextColors.GOLD, TextStyles.BOLD, AltCodes.ARROW_RIGHT.getSign() + " "), sentence)
                    .onClick(TextActions.executeCallback(this)).build();
        }

        this.player = player;
    }

    public void display(Player player) {
        Messager.sendMessage(player, sentence, Optional.<Messager.Prefix>empty());
    }

    public List<DialogueAction> getAction() {
        return actions;
    }

    public String getSentence() {
        return sentence;
    }

    @Override
    public void accept(CommandSource commandSource) {
        Optional<UserPlayer> temp = Avatar.INSTANCE.getUserManager().findUserPlayer(this.player);
        if(temp.isPresent() && temp.get().getCurrentDialogue() != null && temp.get().getCurrentDialogue().hasChoiceID(this.id)) {
            if(conditions != null){
                for(Condition condition: conditions){
                    if(!condition.isValid(player)){
                        condition.sendErrorMessage(player);
                        return;
                    }
                }
            }
            Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().removeDialogue();

            //if all condition are valid, continue with action
            for (DialogueAction action : this.actions)
                action.doWork(player);

            Sponge.getEventManager().post(new DialogueEvent.ChoiceClicked(Cause.source(Avatar.INSTANCE.getPluginContainer()).build(), this.getId(), temp.get()));
        }

    }

    public String getId() {
        return id;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
