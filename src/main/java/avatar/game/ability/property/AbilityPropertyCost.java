package avatar.game.ability.property;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;
import avatar.game.user.User;
import avatar.game.user.stats.Stats;
import org.spongepowered.api.text.Text;

/**
 * The cost to use this ability
 */
public class AbilityPropertyCost extends AbilityProperty {

    private int cost;
    private Stats.StatType costType;

    public AbilityPropertyCost(String displayName, Ability ability, int cost, Stats.StatType type) {
        super(displayName, ability, AbilityStage.REQUIREMENT_CHECK);

        this.cost = cost;
        this.costType = type;
    }

    public void refund(){
        User user = this.ability.getOwner();
        if(user.getStats().hasStat(costType)){
            user.getStats().getStat(costType).get().add(cost);
        }
    }

    @Override
    public boolean validate() {
        User user = this.ability.getOwner();
        if(user.getStats().hasStat(costType)){
            if(user.getStats().getStat(costType).get().canAfford(cost)){
                user.getStats().getStat(costType).get().subtract(cost);
                return true;
            }
        }
        return false;
    }

    @Override
    public Text getFailMessage() {
        return null;
    }
}
