package avatar.util.particles.effects;

import org.spongepowered.api.world.Location;

public class TestEffect extends AbstractEffect {

    public TestEffect(EffectData effectData) {
        super(effectData);
    }

    @Override
    public void play() {
        Location at = effectData.getCenter().copy();
        for(int i = 0; i < 50; i++){
            at = at.add(0.1, 0, 0);
            effectData.setDisplayAt(at);
            playParticle();
        }
    }
}
