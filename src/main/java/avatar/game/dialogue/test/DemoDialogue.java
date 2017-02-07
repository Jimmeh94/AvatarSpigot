package avatar.game.dialogue.test;

import avatar.game.dialogue.Dialogue;
import avatar.game.dialogue.actions.DisplayDialogue;
import avatar.game.dialogue.actions.GiveQuest;
import avatar.game.dialogue.displayable.Choice;
import avatar.game.dialogue.displayable.DisplaySentence;
import avatar.game.dialogue.displayable.Sentence;
import avatar.game.quest.QuestReference;
import avatar.game.user.UserPlayer;

public class DemoDialogue extends Dialogue{

    public DemoDialogue(UserPlayer userPlayer){
        super("demo", userPlayer, Data.UNIQUE);

    }

    @Override
    protected void setupInitialDialogue() {
        initialDialogue.add(new Sentence("[Old Man] Will you help me?"));
        initialDialogue.add(new Choice("With what?", "Get more information", getChoiceID(), getPlayer().getPlayer(), new DisplayDialogue(){
            @Override
            public void doWork(UserPlayer userPlayer) {
                userPlayer.getDialogueManager().setCurrentDialogue(new DemoWithWhat(userPlayer)).getDialogueManager().startDialogue();
            }
        }));
        initialDialogue.add(new Choice("Sorry, I can't right now!", "Decline", getChoiceID(), getPlayer().getPlayer(),
                new DisplaySentence("[Old Man] Kids never make time for their elders these days")));
    }

    private class DemoWithWhat extends Dialogue{

        public DemoWithWhat(UserPlayer userPlayer){
            super("demoWithWhat", userPlayer, Data.UNIQUE);
        }


        @Override
        protected void setupInitialDialogue() {
            initialDialogue.add(new Sentence("[Old Man] I seemed to have misplaced my glasses near the southern bridges. Could you find them for me?"));
            initialDialogue.add(new Choice("Sure I can, old timer", "Accept the quest", getChoiceID(), getPlayer().getPlayer(),
                    new DisplaySentence("[Old Man] Thank you!"), new GiveQuest(QuestReference.DEMO)));
            initialDialogue.add(new Choice("Not right now", "Decline the quest", getChoiceID(), getPlayer().getPlayer(),
                    new DisplaySentence("[Old Man] Kids never make time for their elders these days")));
        }
    }

}
