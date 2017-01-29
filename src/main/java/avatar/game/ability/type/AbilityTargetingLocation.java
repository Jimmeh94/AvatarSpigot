package avatar.game.ability.type;

import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.user.User;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;

public abstract class AbilityTargetingLocation extends AbilityTargeting {

    public AbilityTargetingLocation(User owner, double speed, long interval) {
        super(owner, speed, interval);
    }

    @Override
    protected Location setInitialTarget() {
        double range = ((AbilityPropertyBoundRange)getProperty(AbilityPropertyBoundRange.class).get()).getRange();
        if(range == -1){
            //If it's location based, it shouldn't be infinite
            return getOwner().getEntity().getLocation();
        } else {
            BlockIterator blockIterator = new BlockIterator((LivingEntity)owner.getEntity(), (int)range);
            Location give = null;
            while(blockIterator.hasNext()){
                give = blockIterator.next().getLocation();
            }
            return give;
        }
    }
}
