package avatar.events.custom;

import avatar.game.quest.Checkpoint;
import avatar.game.quest.Quest;
import avatar.game.user.UserPlayer;
import org.bukkit.event.HandlerList;
import org.spongepowered.api.event.cause.String;

public abstract class QuestEvent extends CustomEvent {

    private UserPlayer player;
    private Quest quest;

    public QuestEvent(String cause, UserPlayer player, Quest quest) {
        super(cause);

        this.player = player;
        this.quest = quest;
    }

    public Quest getQuest() {
        return quest;
    }

    public UserPlayer getPlayer() {
        return player;
    }

    public static class Start extends QuestEvent{
        private static final HandlerList handlers = new HandlerList();

        public Start(String cause, UserPlayer player, Quest quest) {
            super(cause, player, quest);
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }

    public static class CheckpointComplete extends QuestEvent{
        private static final HandlerList handlers = new HandlerList();

        private Checkpoint checkpoint;

        public CheckpointComplete(String cause, UserPlayer player, Quest quest, Checkpoint checkpoint) {
            super(cause, player, quest);

            this.checkpoint = checkpoint;
        }

        public Checkpoint getCheckpoint() {
            return checkpoint;
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }

    public static class Complete extends QuestEvent{
        private static final HandlerList handlers = new HandlerList();

        public Complete(String cause, UserPlayer player, Quest quest) {
            super(cause, player, quest);
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }

}
