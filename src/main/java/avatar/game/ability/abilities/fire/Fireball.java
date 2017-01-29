package avatar.game.ability.abilities.fire;

import avatar.game.ability.property.AbilityProperty;
import avatar.game.ability.property.AbilityPropertyCost;
import avatar.game.ability.property.AbilityPropertyDuration;
import avatar.game.ability.type.AbilityTargetingLocation;
import avatar.game.user.User;
import avatar.game.user.stats.Stats;
import avatar.util.particles.ParticleUtils;

import java.util.List;

public class Fireball extends AbilityTargetingLocation {

    public Fireball(User owner,double speed, long interval) {
        super(owner, speed, interval);
    }

    @Override
    protected void loadProperties(List<AbilityProperty> properties) {
        properties.add(new AbilityPropertyCost(null, this, 5, Stats.StatType.CHI));
        properties.add(new AbilityPropertyDuration(null, this, 100));
    }

    @Override
    protected void display() {
        ParticleUtils.PlayerBased.displayParticles(effectData);
    }
}
