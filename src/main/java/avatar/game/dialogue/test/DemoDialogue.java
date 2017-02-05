package avatar.game.dialogue.test;

import avatar.Avatar;
import avatar.game.dialogue.Dialogue;
import avatar.game.dialogue.DialogueBuilder;
import avatar.game.dialogue.IDialogueInitiator;
import avatar.game.dialogue.actions.DisplayDialogue;
import avatar.game.dialogue.actions.GiveQuest;
import avatar.game.dialogue.displayable.Choice;
import avatar.game.dialogue.displayable.ChoiceWheel;
import avatar.game.dialogue.displayable.Sentence;
import avatar.game.quest.QuestReference;
import org.bukkit.entity.Player;

public class DemoDialogue implements IDialogueInitiator{

    //TODO Look at redoing this to make it more simple
    //TODO using the same insteance of Dialogue builder to build the inner ones might negate previous set data

    @Override
    public Dialogue build(Player player) {
        DialogueBuilder dialogueBuilder = Avatar.INSTANCE.getDialogueBuilder();

        return dialogueBuilder.stringID("demo").data(Dialogue.Data.UNIQUE)
                .addDisplayable(new Sentence("[Old Man] Will you help me?"))

                .addDisplayable(new ChoiceWheel(
                        new Choice("Yes I will", "Get quest", dialogueBuilder.getPrefixID() + "yes", player, new GiveQuest(QuestReference.DEMO), new DisplayDialogue() {
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testYes").addDisplayable(new Sentence("Oh thank you!")).build(player);
                            }
                        }),
                        new Choice("No I won't", "Deny quest", dialogueBuilder.getPrefixID() + "no", player, new DisplayDialogue() {
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testNo")
                                        .addDisplayable(new Sentence("Come back when you can!")).build(player);
                            }
                        }),
                        new Choice("With what?", "Get more info", dialogueBuilder.getPrefixID() + "with", player, new DisplayDialogue() {
                            @Override
                            public Dialogue buildToDisplay(Player player) {
                                return dialogueBuilder.stringID("testWith")
                                        .addDisplayable(new Sentence(("Some kid stole my glasses. I saw him running towards the southern bridges!")))
                                        .addDisplayable(new ChoiceWheel(
                                                new Choice("I'll find your glasses!", "Get quest", dialogueBuilder.getPrefixID() + "sure", player,
                                                        new GiveQuest(QuestReference.DEMO), new DisplayDialogue() {
                                                    @Override
                                                    public Dialogue buildToDisplay(Player player) {
                                                        return dialogueBuilder.stringID("testYes").addDisplayable(new Sentence("Oh thank you!")).build(player);
                                                    }
                                                }),
                                                new Choice("I can't right now", null, dialogueBuilder.getPrefixID()
                                                        + "danger", player, new DisplayDialogue() {
                                                    @Override
                                                    public Dialogue buildToDisplay(Player player) {
                                                        return dialogueBuilder.stringID("testNo")
                                                                .addDisplayable(new Sentence("Oh okay. Come back when you can!")).build(player);
                                                    }
                                                })))
                                        .build(player);
                            }
                        })).addConditions(null))

                .build(player);
    }
}
