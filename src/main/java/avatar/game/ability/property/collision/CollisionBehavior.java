package avatar.game.ability.property.collision;

import avatar.game.ability.type.Ability;
import avatar.game.ability.type.AbilityTargeting;
import avatar.game.user.User;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public abstract class CollisionBehavior{
    protected AbilityTargeting owner;
    protected boolean collided = false;
    private CollisionCallback[] callback;

    public abstract void clear();

    public CollisionBehavior(AbilityTargeting ability, CollisionCallback... collisionCallback){
        this.owner = ability;
        this.callback = collisionCallback;
    }

    public boolean doCollision(){
        boolean give = true;
        for(CollisionCallback collisionCallback: callback){
            if(!collisionCallback.doCollision(this)){
                give = false;
            }
        }
        return give;
    }

    public boolean isCollided() {
        return collided;
    }

    public AbilityTargeting getAbility() {
        return owner;
    }

    public static class CollideOnBlock extends CollisionBehavior{
        private List<Material> excludeBlocks;
        protected List<Block> collidedBlocks;

        public CollideOnBlock(AbilityTargeting ability, List<Material> excludeBlocks, CollisionCallback... collisionCallback){
            super(ability, collisionCallback);
            this.excludeBlocks = excludeBlocks;
            collidedBlocks = new ArrayList<>();
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

        /** Return true if the block should be ignored **/
        public boolean hasExclusion(Material type) {
            if(type == Material.AIR)
                return true;
            if(excludeBlocks == null){
                return false;
            } else return excludeBlocks.contains(type);
        }

        public List<Block> getCollidedBlocks() {
            return collidedBlocks;
        }
    }

    public static class CollideOnUser extends CollisionBehavior{
        private List<User> collidedUsers;

        public CollideOnUser(AbilityTargeting ability, CollisionCallback... collisionCallback){
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

        public CollideOnAbility(AbilityTargeting ability, CollisionCallback... collisionCallback){
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


}
