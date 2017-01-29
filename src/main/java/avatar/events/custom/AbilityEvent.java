package avatar.events.custom;

import avatar.game.ability.type.Ability;
import org.bukkit.event.HandlerList;

public abstract class AbilityEvent extends CustomEvent {

    private Ability ability;

    public AbilityEvent(Ability ability, String cause) {
        super(cause);

        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    public static class PostFire extends AbilityEvent{

        private static final HandlerList handlers = new HandlerList();

        public PostFire(Ability ability, String cause) {
            super(ability, cause);
        }

        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }
}
