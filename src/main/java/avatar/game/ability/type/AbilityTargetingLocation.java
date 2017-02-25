package avatar.game.ability.type;

import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.user.User;
import avatar.util.directional.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

import java.util.Set;

public abstract class AbilityTargetingLocation extends AbilityTargeting {

    public AbilityTargetingLocation(User owner, double speed) {
        this(owner, speed, 3);
    }

    public AbilityTargetingLocation(User owner, double speed, int history) {
        super(owner, speed, history);
    }

    @Override
    protected Location setInitialTarget() {
        double range = ((AbilityPropertyBoundRange)getProperty(AbilityPropertyBoundRange.class).get()).getRange();
        if(range == -1){
            //If it's location based, it shouldn't be infinite
            return getOwner().getEntity().getLocation();
        } else {
            return LocationUtils.getCenteredLocation(((LivingEntity) owner.getEntity()).getTargetBlock((Set<Material>) null, (int) range).getLocation());
        }
    }
}
