package avatar.events.custom;

import avatar.game.area.Area;
import avatar.game.user.User;
import org.bukkit.event.HandlerList;

public abstract class AreaEvent extends CustomEvent {
    private Area area;
    private User user;

    public AreaEvent(User user, Area area, String cause){
        super(cause);

        this.user = user;
        this.area = area;
    }

    public User getUser() {
        return user;
    }

    public Area getArea() {
        return area;
    }

    public static class Enter extends AreaEvent{
        private static final HandlerList handlers = new HandlerList();

        public Enter(User user, Area area, String cause) {
            super(user, area, cause);
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }

    public static class Exit extends AreaEvent{
        private static final HandlerList handlers = new HandlerList();

        public Exit(User user, Area area, String cause) {
            super(user, area, cause);
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }
}
