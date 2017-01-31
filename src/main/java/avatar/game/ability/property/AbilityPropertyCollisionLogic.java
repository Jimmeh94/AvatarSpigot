package avatar.game.ability.property;

import avatar.Avatar;
import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;
import avatar.game.user.User;
import avatar.util.misc.AABB;
import avatar.util.misc.LocationUtils;
import avatar.util.misc.Vector;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbilityPropertyCollisionLogic extends AbilityProperty {

    protected CollisionBehavior[] collisionBehaviors;

    public AbilityPropertyCollisionLogic(String displayName, Ability ability, CollisionBehavior... collisionBehaviors) {
        super(displayName, ability, AbilityStage.UPDATE);

        this.collisionBehaviors = collisionBehaviors;
    }

    protected abstract boolean collidesAbility(Ability ability);
    protected abstract boolean collidesEntity(Entity user);
    protected abstract List<Block> collidesBlocks();

    public CollisionBehavior[] getCollisionBehaviors() {
        return collisionBehaviors;
    }

    public boolean hasBehavior(Class<? extends CollisionBehavior> c){
        for(CollisionBehavior behavior: collisionBehaviors){
            if(behavior.getClass() == c)
                return true;
        }
        return false;
    }

    public CollisionBehavior getBehavior(Class<? extends CollisionBehavior> c){
        for(CollisionBehavior behavior: collisionBehaviors){
            if(behavior.getClass() == c)
                return behavior;
        }
        return null;
    }

    public boolean hasCollided(){
        for(CollisionBehavior behavior: collisionBehaviors){
            if(behavior.collided)
                return true;
        }
        return false;
    }

    public boolean sameBlock(Location one, Location two){
        return one.getBlockX() == two.getBlockX() && one.getBlockY() == two.getBlockY() && one.getBlockZ() == two.getBlockZ();
    }

    @Override
    public boolean validate() {
        for(CollisionBehavior behavior: collisionBehaviors){
            behavior.clear();
        }

        if(hasBehavior(CollisionBehavior.CollideOnAbility.class)) {
            for (Ability ability : this.ability.getArea().getAbilityManager().getNearbyAbilitiesInChunk(this.ability)) {
                if (collidesAbility(ability)) {
                    ((CollisionBehavior.CollideOnAbility)getBehavior(CollisionBehavior.CollideOnAbility.class)).addAbility(ability);
                }
            }
        }

        if(hasBehavior(CollisionBehavior.CollideOnUser.class)) {
            //check for nearby entities next
            Optional<User> user;
            for (Entity entity : ability.getCenter().getChunk().getEntities()) {
                if (collidesEntity(entity)) {
                    user = Avatar.INSTANCE.getUserManager().findUser(entity);
                    if (user.isPresent()) {
                        ((CollisionBehavior.CollideOnUser)getBehavior(CollisionBehavior.CollideOnUser.class)).addUser(user.get());
                    }
                }
            }
        }

        if(hasBehavior(CollisionBehavior.CollideOnBlock.class)){
            List<Block> blocks = collidesBlocks();
            if(blocks != null && !blocks.isEmpty()){
                ((CollisionBehavior.CollideOnBlock)getBehavior(CollisionBehavior.CollideOnBlock.class)).addBlocks(blocks);
            }
        }

        if(hasCollided()){
            boolean shouldContinue = true;
            for(CollisionBehavior behavior: collisionBehaviors){
                if(behavior.collided){
                    shouldContinue = behavior.doCollision();
                }
            }
            return shouldContinue;
        } else return true;
    }

    /** Shapes **/
    /** Dome **/
    public static class DomeCollisionLogic extends AbilityPropertyCollisionLogic {

        public enum DomeDirection{
            UP(1),
            DOWN(-1);

            protected int delta;

            DomeDirection(int d){this.delta = d;}
        }

        protected double radius;
        protected DomeDirection domeDirection;
        protected List<Location> flatSurface;

        public DomeCollisionLogic(String displayName, Ability ability, double radius, DomeDirection direction) {
            super(displayName, ability);

            this.radius = radius;
            this.domeDirection = direction;

            double copy = new Double(radius);
            while(copy > 0){
                flatSurface.addAll(LocationUtils.getCircleOutline(ability.getCenter(), copy, false));
                copy--;
            }
        }

        public void adjustSurface(Vector.Vector3D vector3d){
            for(Location location: flatSurface){
                location.add(vector3d.getX(), vector3d.getY(), vector3d.getZ());
            }
        }

        @Override
        protected boolean collidesAbility(Ability ability) {
            if(ability.getProperty(CubeCollisionLogic.class).isPresent()){
                CubeCollisionLogic logic = (CubeCollisionLogic) ability.getProperty(CubeCollisionLogic.class).get();

                for(Location vector3d: logic.getBoxLocations()){
                    for(Location location: getCheckableLocations()){
                        if(sameBlock(location, vector3d) && location.distance(logic.ability.getCenter()) <= radius){
                            return true;
                        }
                    }
                }
            } else if(ability.getProperty(SphereCollisionLogic.class).isPresent()){
                SphereCollisionLogic logic = (SphereCollisionLogic) ability.getProperty(SphereCollisionLogic.class).get();
                for(Location location: getCheckableLocations()){
                    if(location.distance(logic.getAbility().getCenter()) <= logic.radius){
                        return true;
                    }
                }
            } else if(ability.getProperty(DomeCollisionLogic.class).isPresent()){
                DomeCollisionLogic logic = (DomeCollisionLogic) ability.getProperty(DomeCollisionLogic.class).get();
                for(Location location: getCheckableLocations()){
                    for(Location temp: logic.getCheckableLocations()){
                        if(sameBlock(location, temp) && location.distance(logic.ability.getCenter()) <= logic.radius){
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        protected boolean collidesEntity(Entity entity) {
            if (entity.getLocation().distance(ability.getCenter()) <= radius) {
                if(domeDirection == DomeDirection.DOWN){
                    if(entity.getLocation().getBlockY() <= ability.getCenter().getBlockY())
                        return true;
                } else if(domeDirection == DomeDirection.UP){
                    if(entity.getLocation().getBlockY() >= ability.getCenter().getBlockY())
                        return true;
                }
            }
            return false;
        }

        @Override
        protected List<Block> collidesBlocks() {
            List<Location> temp = getCheckableLocations();
            List<Block> give = new ArrayList<>();
            for(Location location: temp){
                if(location.distance(ability.getCenter()) > radius)
                    continue;
                if(!((CollisionBehavior.CollideOnBlock)getBehavior(CollisionBehavior.CollideOnBlock.class)).hasExclusion(location.getBlock().getType())){
                    give.add(location.getBlock());
                }
            }
            return give;
        }

        public List<Location> getCheckableLocations(){
            List<Location> blocks = new ArrayList<>();
            blocks.addAll(flatSurface);

            Location left = null, up = null, right = null, down = null, forward = null;
            if(domeDirection == DomeDirection.DOWN || domeDirection == DomeDirection.UP){
                left = ability.getCenter().clone().add(radius, 0, 0);
                up = ability.getCenter().clone().add(0, 0, radius);
                right = ability.getCenter().clone().subtract(radius, 0, 0);
                down = ability.getCenter().clone().subtract(0, 0, radius);

                if(domeDirection == DomeDirection.DOWN){
                    forward = ability.getCenter().clone().subtract(0, radius, 0);
                } else forward = ability.getCenter().clone().add(0, radius, 0);
            }
            blocks.addAll(LocationUtils.getConnectingLine(forward, left));
            blocks.addAll(LocationUtils.getConnectingLine(forward, up));
            blocks.addAll(LocationUtils.getConnectingLine(forward, right));
            blocks.addAll(LocationUtils.getConnectingLine(forward, down));
            return blocks;
        }

        @Override
        public String getFailMessage() {
            return null;
        }
    }

    /** Sphere **/
    public static class SphereCollisionLogic extends AbilityPropertyCollisionLogic {

        protected double radius;

        public SphereCollisionLogic(String displayName, Ability ability, double radius) {
            super(displayName, ability);

            this.radius = radius;
        }

        @Override
        protected boolean collidesAbility(Ability ability) {
            if(ability.getProperty(CubeCollisionLogic.class).isPresent()){
                CubeCollisionLogic logic = (CubeCollisionLogic) ability.getProperty(CubeCollisionLogic.class).get();
                for(Location vector3d: logic.getBoxLocations()){
                    if(vector3d.distance(this.ability.getCenter()) <= radius){
                        return true;
                    }
                }

            } else if(ability.getProperty(SphereCollisionLogic.class).isPresent()){
                SphereCollisionLogic logic = (SphereCollisionLogic) ability.getProperty(SphereCollisionLogic.class).get();
                if(LocationUtils.getConnectingLine(this.ability.getCenter(), ability.getCenter()).size() <= this.radius + logic.radius){
                    return true;
                }

            } else if(ability.getProperty(DomeCollisionLogic.class).isPresent()){
                DomeCollisionLogic logic = (DomeCollisionLogic) ability.getProperty(DomeCollisionLogic.class).get();
                for(Location location: logic.getCheckableLocations()){
                    if(location.distance(logic.getAbility().getCenter()) <= radius){
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        protected boolean collidesEntity(Entity user) {
            return user.getLocation().distance(ability.getCenter()) <= radius;
        }

        @Override
        protected List<Block> collidesBlocks() {
            //get top left corner, .add(radius, radius, radius) to bottom right
            Location start = ability.getCenter().clone().subtract(radius, radius, radius);
            Location end = ability.getCenter().clone().add(radius, radius, radius);
            List<Location> temp = LocationUtils.getCubeLocations(start, end);
            List<Block> give = new ArrayList<>();
            for(Location location: temp){
                if(location.distance(ability.getCenter()) > radius)
                    continue;

                if(!((CollisionBehavior.CollideOnBlock)getBehavior(CollisionBehavior.CollideOnBlock.class)).hasExclusion(location.getBlock().getType())){
                    give.add(location.getBlock());
                }
            }
            return give;
        }

        @Override
        public String getFailMessage() {
            return null;
        }
    }

    //*** Square ***
    public static class CubeCollisionLogic extends AbilityPropertyCollisionLogic {

        private AABB hitbox;

        public CubeCollisionLogic(String displayName, Ability ability, double x, double y, double z){
            super(displayName, ability);

            Location temp = ability.getOwner().getEntity().getLocation().add(0, 1, 0);
            this.hitbox = new AABB(temp.getX() - x/2, temp.getY() - y/2, temp.getZ() - z/2,
                    temp.getX() + x/2, temp.getY() + y/2, temp.getZ() + z/2);
        }

        public List<Location> getBoxLocations(){
            return LocationUtils.getCubeLocations(hitbox.getMin(), hitbox.getMax());
        }

        @Override
        public String getFailMessage() {
            return null;
        }

        @Override
        public boolean collidesAbility(Ability ability) {
            if(ability.getProperty(CubeCollisionLogic.class).isPresent()){
                CubeCollisionLogic logic = (CubeCollisionLogic) ability.getProperty(CubeCollisionLogic.class).get();
                if(this.hitbox.intersects(logic.hitbox)){
                    return true;
                }
            } else if(ability.getProperty(SphereCollisionLogic.class).isPresent()){
                SphereCollisionLogic logic = (SphereCollisionLogic) ability.getProperty(SphereCollisionLogic.class).get();
                for(Location vector3d: getBoxLocations()){
                    if(vector3d.distance(ability.getCenter()) <= logic.radius){
                        return true;
                    }
                }
            } else if(ability.getProperty(DomeCollisionLogic.class).isPresent()){
                DomeCollisionLogic logic = (DomeCollisionLogic) ability.getProperty(DomeCollisionLogic.class).get();

                for(Location vector3d: getBoxLocations()){
                    for(Location location: logic.getCheckableLocations()){
                        if(sameBlock(location, vector3d) && location.distance(logic.ability.getCenter()) <= logic.radius){
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        protected boolean collidesEntity(Entity user) {
            for(Location location: getBoxLocations()){
                if(sameBlock(location, user.getLocation())){
                    return true;
                }
            }
            return false;
        }

        @Override
        protected List<Block> collidesBlocks() {
            List<Block> give = new ArrayList<>();
            for(Location location: getBoxLocations()){
                if(!((CollisionBehavior.CollideOnBlock)getBehavior(CollisionBehavior.CollideOnBlock.class)).hasExclusion(location.getBlock().getType())){
                    give.add(location.getBlock());
                }
            }
            return give;
        }

        public void offset(Location oldCenter, Location center) {
            this.hitbox = hitbox.offset(LocationUtils.getOffsetBetween(oldCenter, center));
        }
    }
}
