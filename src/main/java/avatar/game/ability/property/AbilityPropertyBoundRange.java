package avatar.game.ability.property;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;

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
        return range == INFINITE || ability.getCenter().distance(ability.getFiredFrom()) < range;
    }

    @Override
    public boolean validate() {
        return inRange();
    }

    @Override
    public void reset() {

    }

    @Override
    public String getFailMessage() {
        return null;
    }

    public double getRange() {
        return range;
    }
}
