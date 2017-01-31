package avatar.game.ability.type;

import avatar.Avatar;
import avatar.game.ability.AbilityStage;
import avatar.game.ability.property.AbilityProperty;
import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.ability.property.AbilityPropertyCollisionLogic;
import avatar.game.user.User;
import avatar.util.misc.LocationUtils;
import avatar.util.particles.effects.EffectData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

/**
 * Use this if the ability needs to move somewhere.
 * Self casting/instant ability should just extend Ability
 */
public abstract class AbilityTargeting extends Ability implements Runnable{

    private Location target;
    protected Location[] history;
    /**
     * The scale at which to move the ability per update.
     * example: 1.0 speed would be to advance the ability by 1 block each update
     */
    private double speed;
    private BukkitTask task;
    private Long interval;
    protected EffectData effectData;

    protected abstract Location setInitialTarget();
    protected abstract EffectData setEffectData();
    protected abstract void display();

    public AbilityTargeting(User owner, double speed, long interval) {
        this(owner, speed, interval, 3);
    }

    public AbilityTargeting(User owner, double speed, long interval, int history) {
        super(owner);

        this.interval = interval;
        this.history = new Location[history];

        if(!getProperty(AbilityPropertyBoundRange.class).isPresent()){
            addProperty(new AbilityPropertyBoundRange(null, this, AbilityPropertyBoundRange.INFINITE));
        }

        this.speed = speed;
        this.target = setInitialTarget();

        effectData = setEffectData();
    }

    @Override
    public void fire(){
        super.fire();

        if(this.stage != AbilityStage.FINISH){
            task = Bukkit.getScheduler().runTaskTimer(Avatar.INSTANCE, this, interval, 0L);
        }
    }

    @Override
    public void cancel(String cause){
        super.cancel(cause);

        task.cancel();
    }

    @Override
    public void run(){
        //set the location and check if at target
        //check on properties for UPDATE
        //if any of those !validate, stop the ability
        setLocationInfo();
        effectData.setDisplayAt(getCenter());
        if(this.getCenter().distance(this.getTarget()) <= 0.75){
            this.cancel(null);
            return;
        }

        for(AbilityProperty property: properties){
            if(property.checkNow(stage)){
                if(!property.validate()){
                    this.cancel(property.getFailMessage());
                    return;
                }
            }
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

        if(getProperty(AbilityPropertyCollisionLogic.CubeCollisionLogic.class).isPresent()){
            ((AbilityPropertyCollisionLogic.CubeCollisionLogic)getProperty(AbilityPropertyCollisionLogic.CubeCollisionLogic.class).get()).offset(oldCenter, center);
        }

        if(!this.area.contains(this.center)){
            if(this.area != null){
                area.getAbilityManager().remove(this);
                if(area.isInstanced(this)){
                    area.getInstance(this).get().removeAbility(this);
                }
            }

            this.area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(this.center).get();
            this.area.getAbilityManager().add(this);
            if (area.isInstanced(owner)) {
                area.getInstance(owner).get().addAbility(this);
            }
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
