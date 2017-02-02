package avatar.game.ability.property.collision;

public interface CollisionCallback{
    /**
     * Return true if the ability should continue after collision
     * @param collisionBehavior
     * @return
     */
    boolean doCollision(CollisionBehavior collisionBehavior);
}
