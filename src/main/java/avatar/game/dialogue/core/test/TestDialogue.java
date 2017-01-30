package avatar.game.dialogue.core.test;

import avatar.Avatar;
import avatar.game.dialogue.core.Dialogue;
import avatar.game.dialogue.core.DialogueBuilder;
import avatar.game.dialogue.core.IDialogueInitiator;
import avatar.game.dialogue.core.actions.DisplayDialogue;
import avatar.game.dialogue.core.actions.GiveQuest;
import avatar.game.dialogue.core.displayable.Choice;
import avatar.game.dialogue.core.displayable.ChoiceWheel;
import avatar.game.dialogue.core.displayable.Sentence;
import avatar.game.quest.QuestReference;
import org.bukkit.entity.Player;

public class TestDialogue implements IDialogueInitiator{

    @Override
    public Dialogue build(Player player) {
        DialogueBuilder dialogueBuilder = Avatar.INSTANCE.getDialogueBuilder();

        return dialogueBuilder.stringID("test")
                .addDisplayable(new Sentence("[Old Man] Will you help me?"))
                .addDisplayable(new ChoiceWheel(
                        new Choice("Yes I will", "Get quest", IDialogueInitiator.getIDPrefix(dialogueBuilder) + "yes", player, new GiveQuest(QuestReference.TEST), new DisplayDialogue(){
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testYes").addDisplayable(new Sentence("Oh thank you!")).build(player);
                            }
                        }),
                        new Choice("No I won't", "Deny quest", IDialogueInitiator.getIDPrefix(dialogueBuilder) + "no", player, new DisplayDialogue() {
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testNo")
                                        .addDisplayable(new Sentence("Well up yours buddy!")).build(player);
                            }
                        }),
                        new Choice("With what?", "Get more info", IDialogueInitiator.getIDPrefix(dialogueBuilder) + "with", player, new DisplayDialogue() {
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testWith")
                                        .addDisplayable(new Sentence(("I lost some candy in my van... ;)")))
                                        .addDisplayable(new ChoiceWheel(
                                                new Choice("Sure, I loooooove candy", "Get quest", IDialogueInitiator.getIDPrefix(dialogueBuilder)+ "sure", player,
                                                        new GiveQuest(QuestReference.TEST), new DisplayDialogue(){
                                                    @Override
                                                    public Dialogue buildToDisplay(Player player) {
                                                        return dialogueBuilder.stringID("testYes").addDisplayable(new Sentence("Oh thank you!")).build(player);
                                                    }
                                                }),
                                                new Choice("Stranger danger!", null, IDialogueInitiator.getIDPrefix(dialogueBuilder)
                                                        + "danger", player, new DisplayDialogue(){
                                                    @Override
                                                    public Dialogue buildToDisplay(Player player) {
                                                        return dialogueBuilder.stringID("testNo")
                                                                .addDisplayable(new Sentence("Well up yours buddy!")).build(player);
                                                    }
                                                })))
                                        .build(player);
                            }
                        })).addConditions(null))
                .build(player);
    }
}
