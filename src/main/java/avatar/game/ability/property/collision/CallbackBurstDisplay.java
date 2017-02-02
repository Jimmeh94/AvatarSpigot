package avatar.game.ability.property.collision;

import avatar.util.particles.effectData.EffectData;

public class CallbackBurstDisplay implements CollisionCallback {
    private EffectData effectData;

    public CallbackBurstDisplay(EffectData effectData){this.effectData = effectData;}


    @Override
    public boolean doCollision(CollisionBehavior collisionBehavior) {
        effectData.setDisplayAt(collisionBehavior.getAbility().getCenter());
        effectData.display();
        return true;
    }
}
