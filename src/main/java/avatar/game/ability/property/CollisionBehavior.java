package avatar.game.ability.property;

import avatar.game.ability.type.Ability;
import avatar.game.user.User;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public abstract class CollisionBehavior{
    protected Ability owner;
    protected boolean collided = false;
    private CollisionCallback callback;

    public abstract void clear();

    public CollisionBehavior(Ability ability, CollisionCallback collisionCallback){
        this.owner = ability;
        this.callback = collisionCallback;
    }

    public boolean doCollision(){
        return callback.doCollision(this);
    }

    public static class CollideOnBlock extends CollisionBehavior{
        private List<Material> excludeBlocks;
        protected List<Block> collidedBlocks;

        public CollideOnBlock(Ability ability, CollisionCallback collisionCallback, List<Material> excludeBlocks){
            super(ability, collisionCallback);
            this.excludeBlocks = excludeBlocks;
        }

        public List<Material> getExcludeBlocks() {return excludeBlocks;}

        public void addBlock(Block block){
            if(!collidedBlocks.contains(block) && excludeBlocks != null && !excludeBlocks.contains(block.getType())){
                collidedBlocks.add(block);
                collided = true;
            }
        }

        @Override
        public void clear() {
            collided = false;
            collidedBlocks.clear();
        }

        public void addBlocks(List<Block> blocks) {
            collidedBlocks = blocks;
            collided = true;
        }

        public boolean hasExclusion(Material type) {
            return excludeBlocks.contains(type);
        }
    }

    public static class CollideOnUser extends CollisionBehavior{
        private List<User> collidedUsers;

        public CollideOnUser(Ability ability, CollisionCallback collisionCallback){
            super(ability, collisionCallback);
            collidedUsers = new ArrayList<>();
        }

        public List<User> getCollidedUsers() {
            return collidedUsers;
        }

        public void addUser(User user){
            if(!collidedUsers.contains(user)){
                collidedUsers.add(user);
                collided = true;
            }
        }

        @Override
        public void clear() {
            collided = false;
            collidedUsers.clear();
        }
    }

    public static class CollideOnAbility extends CollisionBehavior{
        private List<Ability> collidedAbilities;

        public CollideOnAbility(Ability ability, CollisionCallback collisionCallback){
            super(ability, collisionCallback);
            collidedAbilities = new ArrayList<>();
        }

        @Override
        public void clear() {
            collided = false;
            collidedAbilities.clear();
        }

        public void addAbility(Ability ability){
            if(!collidedAbilities.contains(ability)){
                collidedAbilities.add(ability);
                collided = true;
            }
        }

        public List<Ability> getCollidedAbilities() {
            return collidedAbilities;
        }
    }

    public interface CollisionCallback{
        /** Return true if the ability should continue after collision **/
        boolean doCollision(CollisionBehavior collisionBehavior);
    }
}
