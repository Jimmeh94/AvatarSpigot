package avatar.game.ability.property;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;

/**
 * How long the ability lasts
 */
public class AbilityPropertyDuration extends AbilityProperty {

    private int seconds;
    private long whenShot = System.currentTimeMillis();

    public AbilityPropertyDuration(String displayName, Ability ability, int seconds){
        super(displayName, ability, AbilityStage.UPDATE);

        this.seconds = seconds;
    }

    @Override
    public boolean validate() {
        return ((System.currentTimeMillis() - whenShot)/1000 < seconds);
    }

    @Override
    public String getFailMessage() {
        return null;
    }

    @Override
    public String getLore() {
        return "Duration: " + seconds + "s";
    }
}
