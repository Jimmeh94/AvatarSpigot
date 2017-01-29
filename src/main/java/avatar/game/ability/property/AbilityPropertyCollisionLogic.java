package avatar.game.ability.property;

import avatar.Avatar;
import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;
import avatar.game.user.User;
import avatar.util.misc.AABB;
import avatar.util.misc.LocationUtils;
import avatar.util.misc.Vector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbilityPropertyCollisionLogic extends AbilityProperty {

    protected List<Ability> collidedAbilities = new ArrayList<>();
    protected List<User> collidedUsers = new ArrayList<>();

    public AbilityPropertyCollisionLogic(String displayName, Ability ability) {
        super(displayName, ability, AbilityStage.UPDATE);
    }

    public List<User> getCollidedUsers() {
        return collidedUsers;
    }

    public List<Ability> getCollidedAbilities() {
        return collidedAbilities;
    }

    protected abstract boolean collides(Ability ability);

    /* Shapes */
    //*** Dome ***
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

        protected double getExtendedY(){
            return ability.getCenter().getY() + radius * domeDirection.delta;
        }

        @Override
        protected boolean collides(Ability ability) {
            if(ability.getProperty(CubeCollisionLogic.class).isPresent()){
                CubeCollisionLogic logic = (CubeCollisionLogic) ability.getProperty(CubeCollisionLogic.class).get();

                for(Location vector3d: logic.getBoxLocations()){
                    if(domeDirection == DomeDirection.UP){
                        if(vector3d.getBlockY() < this.ability.getCenter().getBlockY())
                            continue;
                        else {
                            if(this.ability.getCenter().distance(vector3d) <= radius)
                                return true;
                        }
                    } else if(domeDirection == DomeDirection.DOWN){
                        if(vector3d.getBlockY() > this.ability.getCenter().getBlockY())
                            continue;
                        else {
                            if(this.ability.getCenter().distance(vector3d) <= radius)
                                return true;
                        }
                    }
                }
                return false;
            } else if(ability.getProperty(SphereCollisionLogic.class).isPresent()){
                SphereCollisionLogic logic = (SphereCollisionLogic) ability.getProperty(SphereCollisionLogic.class).get();
                double full = logic.radius + radius;
                //Full is the absolute furthest away they can be

                if(ability.getCenter().distance(this.ability.getCenter()) <= full) {
                    if (domeDirection == DomeDirection.UP) {
                        //Meaning the dome's radius won't extend down
                        if (ability.getCenter().getBlockY() < this.ability.getCenter().getBlockY()) {
                            //this means the sphere is below the dome, meaning it can only be the sphere's radius away
                            for(Location location: flatSurface){
                                if(ability.getCenter().distance(location) <= logic.radius){
                                    return true;
                                }
                            }
                            return false;
                        }
                        return true;
                    } else if (domeDirection == DomeDirection.DOWN) {
                        if (ability.getCenter().getBlockY() > this.ability.getCenter().getBlockY()) {
                            for(Location location: flatSurface){
                                if(ability.getCenter().distance(location) <= logic.radius){
                                    return true;
                                }
                            }
                            return false;
                        }
                        return true;
                    }
                } else return false;
            } else if(ability.getProperty(DomeCollisionLogic.class).isPresent()){
                DomeCollisionLogic logic = (DomeCollisionLogic) ability.getProperty(DomeCollisionLogic.class).get();
                double full = logic.radius + radius;

                if(domeDirection != logic.domeDirection){
                    //facing each other (opposite directions)
                    if(ability.getCenter().distance(this.ability.getCenter()) <= full){
                        return true;
                    } else return false;
                }

                //We know they are facing the same direction
                for(Location location: logic.flatSurface){
                    if(location.distance(this.ability.getCenter()) <= radius){
                        return true;
                    }
                }
                return false;
            }
            return false;
        }

        @Override
        public boolean validate() {
            collidedAbilities.clear();
            collidedUsers.clear();

            for(Ability ability: this.ability.getArea().getAbilityManager().getNearbyAbilitiesInChunk(this.ability)){
                if(collides(ability)){
                    collidedAbilities.add(ability);
                }
            }

            //check for nearby entities next
            Optional<User> user;
            for(Entity entity: ability.getCenter().getChunk().getEntities()){
                if(entity.getLocation().distance(ability.getCenter()) <= radius){
                    user = Avatar.INSTANCE.getUserManager().findUser(entity);
                    if(user.isPresent()){
                        collidedUsers.add(user.get());
                    }
                }
            }

            if(collidedAbilities.size() > 0 || collidedUsers.size() > 0){
                //do something here, such as damage the things
                //if ability should expire after, return false
                return false;
            } else return true;
        }

        @Override
        public String getFailMessage() {
            return null;
        }
    }

    //*** Sphere ***
    public static class SphereCollisionLogic extends AbilityPropertyCollisionLogic {

        protected double radius;

        public SphereCollisionLogic(String displayName, Ability ability, double radius) {
            super(displayName, ability);

            this.radius = radius;
        }

        @Override
        public boolean validate() {
            collidedAbilities.clear();
            collidedUsers.clear();

            for(Ability ability: this.ability.getArea().getAbilityManager().getNearbyAbilitiesInChunk(this.ability)){
                if(collides(ability)){
                    collidedAbilities.add(ability);
                }
            }

            //check for nearby entities next
            Optional<User> user;
            for(Entity entity: ability.getCenter().getChunk().getEntities()){
                if(entity.getLocation().distance(ability.getCenter()) <= radius){
                    user = Avatar.INSTANCE.getUserManager().findUser(entity);
                    if(user.isPresent()){
                        collidedUsers.add(user.get());
                    }
                }
            }

            if(collidedAbilities.size() > 0 || collidedUsers.size() > 0){
                //do something here, such as damage the things
                //if ability should expire after, return false
                return false;
            } else return true;
        }

        @Override
        protected boolean collides(Ability ability) {
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
                double full = logic.radius + radius;
                //Full is the absolute furthest away they can be

                if(ability.getCenter().distance(this.ability.getCenter()) <= full) {
                    if (logic.domeDirection == DomeCollisionLogic.DomeDirection.UP) {
                        //Meaning the dome's radius won't extend down
                        if (this.ability.getCenter().getBlockY() < ability.getCenter().getBlockY()) {
                            //this means the sphere is below the dome, meaning it can only be the sphere's radius away
                            for(Location location: logic.flatSurface){
                                if(ability.getCenter().distance(location) <= logic.radius){
                                    return true;
                                }
                            }
                            return false;
                        }
                        return true;
                    } else if (logic.domeDirection == DomeCollisionLogic.DomeDirection.DOWN) {
                        if (this.ability.getCenter().getBlockY() > ability.getCenter().getBlockY()) {
                            for(Location location: logic.flatSurface){
                                if(ability.getCenter().distance(location) <= logic.radius){
                                    return true;
                                }
                            }
                            return false;
                        }
                        return true;
                    }
                } else return false;
            }
            return false;
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
            List<Location> give = new ArrayList<>();
            for(double y = new Double(hitbox.getMin().getY()); y <= hitbox.getMax().getY(); y += .01){
                for(double x = new Double(hitbox.getMin().getX()); x <= hitbox.getMax().getX(); x += .01){
                    for(double z = new Double(hitbox.getMin().getZ()); z <= hitbox.getMax().getZ(); z += .01){
                        give.add(new Location(Bukkit.getWorlds().get(0), x, y, z));
                    }
                }
            }
            return give;
        }

        @Override
        public boolean validate() {
            collidedAbilities.clear();
            collidedUsers.clear();

            for(Ability ability: this.ability.getArea().getAbilityManager().getNearbyAbilitiesInChunk(this.ability)){
                if(collides(ability)){
                    collidedAbilities.add(ability);
                }
            }

            //check for nearby entities next
            Optional<User> user;
            for(Entity entity: ability.getCenter().getChunk().getEntities()){
                if(this.hitbox.contains(entity.getLocation())){
                    user = Avatar.INSTANCE.getUserManager().findUser(entity);
                    if(user.isPresent()){
                        collidedUsers.add(user.get());
                    }
                }
            }

            if(collidedAbilities.size() > 0 || collidedUsers.size() > 0){
                //do something here, such as damage the things
                //if ability should expire after, return false
                return false;
            } else return true;
        }

        @Override
        public String getFailMessage() {
            return null;
        }

        @Override
        public boolean collides(Ability ability) {
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
                    if(logic.domeDirection == DomeCollisionLogic.DomeDirection.UP){
                        if(vector3d.getBlockY() < ability.getCenter().getBlockY())
                            continue;
                        else {
                            if(ability.getCenter().distance(vector3d) <= logic.radius)
                                return true;
                        }
                    } else if(logic.domeDirection == DomeCollisionLogic.DomeDirection.DOWN){
                        if(vector3d.getBlockY() > ability.getCenter().getBlockY())
                            continue;
                        else {
                            if(ability.getCenter().distance(vector3d) <= logic.radius)
                                return true;
                        }
                    }
                }
                return false;
            }
            return false;
        }

        public void offset(Location oldCenter, Location center) {
            this.hitbox = hitbox.offset(LocationUtils.getOffsetBetween(oldCenter, center));
        }
    }
}
