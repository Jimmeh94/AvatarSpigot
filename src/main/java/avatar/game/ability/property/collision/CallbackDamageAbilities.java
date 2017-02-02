package avatar.game.ability.property.collision;

public class CallbackDamageAbilities implements CollisionCallback {
    @Override
    public boolean doCollision(CollisionBehavior collisionBehavior) {
        if(collisionBehavior instanceof CollisionBehavior.CollideOnAbility){
            
        }
        return false;
    }
}
