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

/**
 * Use this if the ability needs to move somewhere.
 * Self casting/instant ability should just extend Ability
 */
public abstract class AbilityTargeting extends Ability implements Runnable{

    private Location target;
    /**
     * The scale at which to move the ability per update.
     * example: 1.0 speed would be to advance the ability by 1 block each update
     */
    private double speed;
    private BukkitTask task;
    private Long interval;
    protected EffectData effectData;

    protected abstract Location setInitialTarget();
    protected abstract void display();

    public AbilityTargeting(User owner, double speed, long interval) {
        super(owner);

        this.interval = interval;

        if(!getProperty(AbilityPropertyBoundRange.class).isPresent()){
            addProperty(new AbilityPropertyBoundRange(null, this, AbilityPropertyBoundRange.INFINITE));
        }

        this.speed = speed;
        this.target = setInitialTarget();

        effectData = EffectData.builder().amount(50).center(getCenter()).particle(ParticleTypes.FLAME).build();
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
        if(this.getCenter().distance(this.getTarget()) <= 0.5){
            this.cancel(null);
        }

        for(AbilityProperty property: properties){
            if(property.checkNow(stage)){
                if(!property.validate()){
                    this.cancel(property.getFailMessage());
                }
            }
        }

        if(this.stage != AbilityStage.FINISH){
            display();
        }
    }



    protected void setLocationInfo(){
        this.oldCenter = center.clone();
        this.center = adjustCenter();
        if(this.center == null)
            return;

        this.locationChunk = center.getChunk();

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
        Location step = LocationUtils.getNextLocation(getCenter(), target, speed);

        if(getProperty(AbilityPropertyCollisionLogic.DomeCollisionLogic.class).isPresent()){
            ((AbilityPropertyCollisionLogic.DomeCollisionLogic)getProperty(AbilityPropertyCollisionLogic.DomeCollisionLogic.class)
                    .get()).adjustSurface(LocationUtils.getOffsetBetween(center, step));
        }

        return step;
    }

    public void setTarget(Location target) {
        this.target = target;
    }

    public double getSpeed() {
        return speed;
    }
}
