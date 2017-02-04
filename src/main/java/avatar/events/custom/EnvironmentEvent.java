package avatar.events.custom;

import avatar.game.entity.hologram.environment.EnvironmentInteractable;
import org.bukkit.event.HandlerList;

public abstract class EnvironmentEvent extends CustomEvent {

    public EnvironmentEvent(String cause) {
        super(cause);
    }

    public static class ServerInteractable extends EnvironmentEvent{

        private static final HandlerList handlers = new HandlerList();
        private EnvironmentInteractable environmentInteractable;

        public ServerInteractable(EnvironmentInteractable environmentInteractable, String cause) {
            super(cause);

            this.environmentInteractable = environmentInteractable;
        }

        public EnvironmentInteractable getEnvironmentInteractable() {
            return environmentInteractable;
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }
}
