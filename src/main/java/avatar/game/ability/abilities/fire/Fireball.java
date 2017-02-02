package avatar.game.ability.abilities.fire;

import avatar.game.ability.property.*;
import avatar.game.ability.property.collision.CallbackDestroyBlocks;
import avatar.game.ability.property.collision.CollisionBehavior;
import avatar.game.ability.type.AbilityTargetingLocation;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.game.user.stats.Stats;
import avatar.util.misc.LocationUtils;
import avatar.util.misc.Vector;
import avatar.util.particles.ParticleUtils;
import avatar.util.particles.effectData.DisplayProfile;
import avatar.util.particles.effectData.EffectData;
import avatar.util.particles.effectData.PlayerBasedEffectData;
import avatar.util.particles.effects.SphereEffect;
import org.bukkit.Particle;

import java.util.List;

public class Fireball extends AbilityTargetingLocation {

    private SphereEffect sphereEffect;

    public Fireball(User owner,double speed, long interval) {
        super(owner, speed, interval, 5);

        sphereEffect = new SphereEffect(effectData, ParticleUtils.Loaded.SPHERE_025R, 0L, 0L, -1);
    }

    @Override
    protected void loadProperties(List<AbilityProperty> properties) {

        properties.add(new AbilityPropertyCost(null, this, 5, Stats.StatType.CHI));
        properties.add(new AbilityPropertyDuration(null, this, 100));
        properties.add(new AbilityPropertyBoundRange(null, this, 50));
        properties.add(new AbilityPropertyCollisionLogic.CubeCollisionLogic(null, this, 3, 3, 3, new CollisionBehavior.CollideOnBlock(this,
                null, new CallbackDestroyBlocks(new PlayerBasedEffectData(getCenter(), (UserPlayer)owner, DisplayProfile.builder().particle(Particle.BLOCK_CRACK).amount(15).build())))));
    }

    @Override
    protected EffectData setEffectData() {
        return new PlayerBasedEffectData(getCenter(), (UserPlayer)owner, DisplayProfile.builder().particle(Particle.FLAME).amount(50).build(),
                DisplayProfile.builder().particle(Particle.FLAME).particleOffsets(0.15, 0.15, 0.15).amount(75).build(),
                DisplayProfile.builder().particle(Particle.SMOKE_LARGE).particleOffsets(0.2, 0.2, 0.2).amount(100).build());
    }

    @Override
    protected void display() {
        //ball itself at displayAt
        //need a trail of particles behind it

        effectData.setActiveDisplayProfile(effectData.getDisplayProfiles()[0]);
        sphereEffect.play();

        for(int i = 0; i < 5; i++){
            if(history[i] == null)
                continue;
            Vector.Vector3D vector3D = LocationUtils.getOffsetBetween(effectData.getDisplayAt(), history[i]);
            effectData.setActiveDisplayProfile(effectData.getDisplayProfiles()[i == 0 ? 1 : 2]);
            effectData.adjustDisplayProfile(vector3D);
            effectData.display();
        }
    }
}
