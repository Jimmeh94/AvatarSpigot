package avatar.game.quest.condition;

import avatar.events.custom.AbilityEvent;
import avatar.game.ability.type.Ability;
import org.bukkit.entity.Player;

public class FireAbility extends Condition {

    private Class<? extends Ability> ability;

    public FireAbility(Player player, Class<? extends Ability> ability) {
        super(player);
        this.ability = ability;
    }


    public void handle(AbilityEvent.PostFire postFire) throws Exception {
        if(postFire.getAbility().getClass().getCanonicalName().equals(ability.getCanonicalName())){
            valid = true;
        }
    }
}
