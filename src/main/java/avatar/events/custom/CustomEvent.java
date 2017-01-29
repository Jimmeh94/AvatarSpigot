package avatar.events.custom;

import org.bukkit.event.Event;

public abstract class CustomEvent extends Event {

    protected String cause;

    public CustomEvent(String cause){this.cause = cause;}

    public String getCause() {
        return cause;
    }
}
