package avatar.game.ability.property;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;
import org.spongepowered.api.text.Text;

/**
 * How far the ability can travel
 */
public class AbilityPropertyBoundRange extends AbilityProperty {

    public static final int INFINITE = -1;

    private double range;

    public AbilityPropertyBoundRange(String displayName, Ability ability, double range) {
        this(displayName, ability, range, AbilityStage.UPDATE);
    }

    public AbilityPropertyBoundRange(String displayName, Ability ability, double range, AbilityStage stage){
        super(displayName, ability, stage);

        this.range = range;
    }

    protected boolean inRange(){
        return range == INFINITE || ability.getCenter().getPosition().distance(ability.getFiredFrom().getPosition()) < range;
    }

    @Override
    public boolean validate() {
        return inRange();
    }

    @Override
    public Text getFailMessage() {
        return null;
    }

    public double getRange() {
        return range;
    }
}
