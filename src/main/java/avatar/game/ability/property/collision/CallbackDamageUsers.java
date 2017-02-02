package avatar.game.ability.property.collision;

public class CallbackDamageUsers implements CollisionCallback {

    @Override
    public boolean doCollision(CollisionBehavior collisionBehavior) {
        if(collisionBehavior instanceof CollisionBehavior.CollideOnUser){

        }
        return false;
    }
}
