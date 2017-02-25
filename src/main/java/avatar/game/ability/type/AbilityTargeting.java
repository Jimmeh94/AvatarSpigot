package avatar.game.ability.type;

import avatar.Avatar;
import avatar.game.ability.AbilityStage;
import avatar.game.ability.property.AbilityProperty;
import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.ability.property.AbilityPropertyCollisionLogic;
import avatar.game.user.User;
import avatar.util.directional.LocationUtils;
import avatar.util.particles.effectData.EffectData;
import org.bukkit.Location;

import java.util.Arrays;

/**
 * Use this if the ability needs to move somewhere.
 * Self casting/instant ability should just extend Ability
 */
public abstract class AbilityTargeting extends Ability{

    private Location target;
    protected Location[] history;
    /**
     * The scale at which to move the ability per update.
     * example: 1.0 speed would be to advance the ability by 1 block each update
     */
    private double speed;
    protected EffectData effectData;

    protected abstract Location setInitialTarget();
    protected abstract EffectData setEffectData();
    protected abstract void display();

    public AbilityTargeting(User owner, double speed) {
        this(owner, speed, 3);
    }

    public AbilityTargeting(User owner, double speed, int history) {
        super(owner);
        this.history = new Location[history];

        if(!getProperty(AbilityPropertyBoundRange.class).isPresent()){
            addProperty(new AbilityPropertyBoundRange(null, this, AbilityPropertyBoundRange.INFINITE));
        }

        this.speed = speed;
        effectData = setEffectData();
        this.target = setInitialTarget();
    }

    public EffectData getEffectData() {
        return effectData;
    }

    public void run(){
        //set the location and check if at target
        //check on properties for UPDATE
        //if any of those !validate, stop the ability
        setLocationInfo();
        effectData.setDisplayAt(getCenter());

        for(AbilityProperty property: properties){
            if(property.checkNow(stage)){
                if(!property.validate()){
                    this.cancel(property);
                    return;
                }
            }
        }

        if(this.getCenter().distance(this.getTarget()) <= 0.75){
            this.cancel(null);
            return;
        }

        if(this.stage != AbilityStage.FINISH){
            display();
        }
    }

    private void shiftHistory(){
        Location[] temp = Arrays.copyOfRange(history, 0, history.length);
        for(int i = 1; i < history.length; i++){
            history[i] = temp[i - 1];
        }
    }

    protected void setLocationInfo(){
        shiftHistory();

        this.oldCenter = center.clone();
        history[0] = oldCenter.clone();
        this.center = adjustCenter();

        if(this.center == null)
            return;

        this.locationChunk = center.getChunk();

        if(getProperty(AbilityPropertyCollisionLogic.DomeCollisionLogic.class).isPresent()){
            ((AbilityPropertyCollisionLogic.DomeCollisionLogic)getProperty(AbilityPropertyCollisionLogic.DomeCollisionLogic.class)
                    .get()).adjustSurface(LocationUtils.getOffsetBetween(oldCenter, center));
        }

        if(!this.area.contains(this.center)){
            if(this.area != null){
                area.getAbilityManager().remove(this);
            }

            this.area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(this.center).get();
            this.area.getAbilityManager().add(this);
        }
    }

    public Location getTarget() {
        return target;
    }

    @Override
    protected Location adjustCenter(){
        return LocationUtils.getNextLocation(getCenter(), target, speed);
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public double getSpeed() {
        return speed;
    }
}
