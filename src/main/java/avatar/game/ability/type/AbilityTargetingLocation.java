package avatar.game.ability.type;

import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.user.User;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;

public abstract class AbilityTargetingLocation extends AbilityTargeting {

    public AbilityTargetingLocation(User owner, double speed, long interval) {
        super(owner, speed, interval);
    }

    @Override
    protected Location setInitialTarget() {
        BlockRay blockRay;
        if(((AbilityPropertyBoundRange)getProperty(AbilityPropertyBoundRange.class).get()).getRange() == -1){
            //If it's location based, it shouldn't be infinite
            return getOwner().getEntity().get().getLocation();
        } else {
            blockRay = BlockRay.from(getOwner().getEntity().get())
                    .distanceLimit(((AbilityPropertyBoundRange)getProperty(AbilityPropertyBoundRange.class).get()).getRange())
                    .skipFilter(BlockRay.allFilter()).build();
        }

        if(blockRay.end().isPresent()){
           return ((BlockRayHit)blockRay.end().get()).getLocation();
        }
        return null;
    }
}
