package avatar.game.quest.condition;

import avatar.Avatar;
import avatar.events.custom.AbilityEvent;
import avatar.game.ability.type.Ability;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FireAbility extends Condition implements Listener {

    private Class<? extends Ability> ability;

    public FireAbility(Class<? extends Ability> ability) {
        this.ability = ability;
    }

    @Override
    public void reset(){
        super.reset();

        unregisterListener();
        setAdditionalStartInfo();
    }

    @Override
    public void setAdditionalStartInfo() {
        Avatar.INSTANCE.getServer().getPluginManager().registerEvents(this, Avatar.INSTANCE);
    }

    @Override
     protected void unregister() {
        AbilityEvent.PostFire.getHandlerList().unregister(this);
    }

    @EventHandler
    public void handle(AbilityEvent.PostFire postFire) throws Exception {
        if(postFire.getAbility().getClass().getCanonicalName().equals(ability.getCanonicalName())){
            valid = true;

            unregisterListener();
        }
    }
}
