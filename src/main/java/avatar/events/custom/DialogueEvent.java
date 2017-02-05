package avatar.events.custom;

import avatar.game.dialogue.Dialogue;
import avatar.game.user.UserPlayer;
import org.bukkit.event.HandlerList;

public abstract class DialogueEvent extends CustomEvent {

    private Dialogue dialogue;
    private UserPlayer userPlayer;

    public DialogueEvent(String cause, UserPlayer userPlayer) {
        super(cause);

        this.dialogue = userPlayer.getDialogueManager().getCurrentDialogue();
        this.userPlayer = userPlayer;
    }

    public UserPlayer getUserPlayer() {
        return userPlayer;
    }

    public static class Displayed extends DialogueEvent{
        private static final HandlerList handlers = new HandlerList();

        public Displayed(String cause, UserPlayer userPlayer) {
            super(cause, userPlayer);
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }

    public static class ChoiceClicked extends DialogueEvent{
        private static final HandlerList handlers = new HandlerList();

        private String choiceID;

        public ChoiceClicked(String cause, String choiceID, UserPlayer userPlayer) {
            super(cause, userPlayer);

            this.choiceID = choiceID;
        }

        public String getChoiceID() {
            return choiceID;
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }
}
