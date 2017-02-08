package avatar.game.dialogue.test;

import avatar.game.dialogue.Dialogue;
import avatar.game.dialogue.actions.DisplayDialogue;
import avatar.game.dialogue.displayable.Choice;
import avatar.game.dialogue.displayable.DisplaySentence;
import avatar.game.dialogue.displayable.Sentence;
import avatar.game.user.UserPlayer;

public class BrothersDialogue extends Dialogue {

    public BrothersDialogue(UserPlayer player) {
        super("demoBrothers", player, Data.UNIQUE);
    }

    @Override
    protected void setupInitialDialogue() {
        initialDialogue.add(new Sentence("[Bobby] Who are you? ...Whoever you are, you're not getting these glasses back!"));
        initialDialogue.add(new Choice("It's okay, I'm just here to talk. Why did you take that man's glasses?", "Inquire", getChoiceID(), getPlayer().getPlayer(), new DisplayDialogue(){
            @Override
            public void doWork(UserPlayer userPlayer) {
                userPlayer.getDialogueManager().setCurrentDialogue(new Inquire(userPlayer)).getDialogueManager().startDialogue();
            }
        }));
        initialDialogue.add(new Choice("Then I'll take it off your corpse", "Battle the brothers", getChoiceID(), getPlayer().getPlayer()));
    }

    private class Inquire extends Dialogue{

        public Inquire(UserPlayer player) {
            super("demoBrothersInquire", player, Data.UNIQUE);
        }

        @Override
        protected void setupInitialDialogue() {
            initialDialogue.add(new Sentence("[Billy] That old man always calls us ugly, so we stole his glasses."));
            initialDialogue.add(new Sentence("[Bobby] Yeah! He had it comin'!"));
            initialDialogue.add(new Choice("How about I take the glasses and talk to him for you?", "Compromise", getChoiceID(), getPlayer().getPlayer(),
                    new DisplaySentence("[Billy] Okay, fine. But you better make him stop teasing us!")));
            initialDialogue.add(new Choice("Oh get over it, and give me the glasses!", "Battle the brothers", getChoiceID(), getPlayer().getPlayer()));
        }
    }
}
