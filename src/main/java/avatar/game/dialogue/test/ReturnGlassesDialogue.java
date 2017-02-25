package avatar.game.dialogue.test;

import avatar.game.dialogue.Dialogue;
import avatar.game.dialogue.displayable.Choice;
import avatar.game.dialogue.displayable.DisplaySentence;
import avatar.game.dialogue.displayable.Sentence;
import avatar.game.user.UserPlayer;

public class ReturnGlassesDialogue extends Dialogue {

    public ReturnGlassesDialogue(UserPlayer player) {
        super("returnGlasses", player, Data.UNIQUE);
    }

    @Override
    protected void setupInitialDialogue() {
        initialDialogue.add(new Sentence("[Old Man] Have you found my glasses?"));

        //Here, we need to check the player's progress in the quest to make sure they have gotten the glasses from the brothers
        initialDialogue.add(new Choice("Yes I did. But you need to leave the brothers alone, okay?", "Give him the glasses", getChoiceID(),
                getPlayer().getPlayer(), new DisplaySentence("Hmmm, okay. You have a deal!")));
    }
}
