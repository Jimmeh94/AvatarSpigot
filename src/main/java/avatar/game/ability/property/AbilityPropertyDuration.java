package avatar.game.ability.property;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;

/**
 * How long the ability lasts
 */
public class AbilityPropertyDuration extends AbilityProperty {

    /**
     * The amount of cycles to perform.
     * This isn't counting how many ticks to run.
     * Set to -1 for infinite/cancellation not based on cycles
     */
    private int cycleLifetime, cycleCounter = 0;

    public AbilityPropertyDuration(String displayName, Ability ability, int cycleLifetime){
        super(displayName, ability, AbilityStage.UPDATE);

        this.cycleLifetime = cycleLifetime;
    }


    private boolean cycleLifetimeValid(){
        if(cycleLifetime == -1){
            return true;
        } else {
            return cycleCounter++ < cycleLifetime;
        }
    }

    @Override
    public boolean validate() {
        return cycleLifetimeValid();
    }

    @Override
    public void reset() {
        cycleCounter = 0;
    }

    @Override
    public String getFailMessage() {
        return null;
    }
}
