package avatar.game.area;

import avatar.events.custom.AreaEvent;
import avatar.game.ability.type.Ability;
import avatar.game.chat.channel.ChatChannel;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.manager.AbilityManager;
import avatar.manager.ListenerManager;
import avatar.util.misc.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * General "area" of the map.
 * Could be used in quest too, such as reach the city of _____
 */

public class Area {

    private AreaShape shape;
    private String displayName;
    private List<User> members = new ArrayList<>();
    private AreaReferences reference;
    private List<Area> children = new ArrayList<>();
    private List<Instance> instances = new ArrayList<>();
    private ChatChannel chatChannel;
    private AbilityManager abilityManager;

    public Area(AreaReferences reference){
        this.shape = reference.getShape();
        this.displayName = reference.getDisplayName();
        this.chatChannel = reference.getChatChannel();
        this.reference = reference;
        abilityManager = new AbilityManager();

        for(AreaReferences r: reference.getChildren()){
            children.add(new Area(r));
        }
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public void addInstance(User user){
        instances.add(new Instance());
        instances.get(instances.size() - 1).addUser(user);
    }

    public boolean isInstanced(User user){
        for(Instance instance: instances){
            if(instance.hasUser(user))
                return true;
        }
        return false;
    }

    public boolean isInstanced(Ability ability){
        for(Instance instance: instances){
            if(instance.hasAbility(ability))
                return true;
        }
        return false;
    }

    public Optional<Instance> getInstance(User user){
        for(Instance instance: instances){
            if(instance.hasUser(user))
                return Optional.of(instance);
        }
        return Optional.empty();
    }

    public Optional<Instance> getInstance(Ability ability){
        for(Instance instance: instances){
            if(instance.hasAbility(ability))
                return Optional.of(instance);
        }
        return Optional.empty();
    }

    public boolean hasChild(AreaReferences reference){
        for(Area area: children){
            if(area.is(reference))
                return true;
        }
        return false;
    }

    public boolean hasChildThatContains(Location location){
        for(Area area: children){
            if(area.contains(location))
                return true;
        }
        return false;
    }

    public Optional<Area> getChildThatContains(Location location){
        if(!this.contains(location))
            return Optional.empty();
        for(Area area: children){
            if(area.contains(location)){
                return area.getChildThatContains(location);
            }
        }
        return Optional.of(this);
    }

    public Optional<Area> getChild(AreaReferences reference){
        for(Area area: children){
            if(area.is(reference))
                return Optional.of(area);
        }
        return Optional.empty();
    }

    public List<Area> getChildren() {
        return children;
    }

    public Location getCenter(){return shape.getCenter();}

    public boolean is(AreaReferences reference){return this.reference == reference;}

    public boolean contains(Location location){
        boolean has;

        if(shape != null && getCenter() != null){
            double x1 = location.getX(), x2 = shape.center.getX();
            double z1 = location.getZ(), z2 = shape.center.getZ();
            double y = location.getY();

            has = LocationUtils.getDistance(x1, x2) <= shape.radius && LocationUtils.getDistance(z1, z2) <= shape.radius
                    && y >= shape.center.getY() && y <= shape.center.getY() + shape.getHeight();

            if(!has) //parent doesn't contain this location, no need searching children
                return false;
        } else has = true; //This means it's a global area

        if(!has) {
            for (Area area : children) {
                has = area.contains(location);
            }
        }

        return has;
    }

    /**
     * You should not call this directly if you have a User entering this area
     * Use the User#enterArea(Area area) method instead
     * @param targetEntity
     */
    public void entering(User targetEntity) {
        if(!members.contains(targetEntity)){
            members.add(targetEntity);

            if(targetEntity instanceof UserPlayer){
                ((UserPlayer)targetEntity).getPlayer().sendMessage("Entering " + displayName);
            }

            Bukkit.getPluginManager().callEvent(new AreaEvent.Enter(targetEntity, this, ListenerManager.getDefaultCause()));
        }
    }

    /**
     * You should not call this direclty if you have a User leaving an area
     * use the User#leaveArea() method instead
     * @param targetEntity
     */
    public void leaving(User targetEntity) {
        if(members.contains(targetEntity)){
            members.remove(targetEntity);

            /*if(targetEntity instanceof UserPlayer){
                ((UserPlayer)targetEntity).getPlayer().get().sendMessage(Text.builder().append(Text.of("Leaving ")).append(displayName).build());
            }*/

            Bukkit.getPluginManager().callEvent(new AreaEvent.Exit(targetEntity, targetEntity.getPresentArea(), ListenerManager.getDefaultCause()));
        }
    }

    public ChatChannel getChatChannel() {
        return chatChannel;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isMember(User entity){
        return members.contains(entity);
    }

    public List<UserPlayer> getPlayersFromMembers() {
        List<UserPlayer> players = new ArrayList<>();
        for(User user: members){
            if(user.isPlayer()){
                players.add(((UserPlayer)user));
            }
        }

        return players;
    }

    /**
     * We assume you've checked Area#contains(location) and that it returned true
     * @param location
     * @return
     */
    public Area getAreaThatContains(Location location) {
        Area give = this;

        for(Area area: children){
            if(area.contains(location))
                give = area.getAreaThatContains(location);
        }

        return give;
    }

    public AreaShape getShape() {
        return shape;
    }

    public static abstract class AreaShape {
        private Location center;
        protected double height, radius;

        public AreaShape(Location center, double height, double radius){
            this.center = center;
            this.height = height;
            this.radius = radius;
        }

        public Location getCenter() {
            return center;
        }

        public double getHeight() {
            return height;
        }

        public double getRadius() {
            return radius;
        }

        public boolean isYWithinBounds(double y){
            return center.getY() <= y && y <= (center.getY() + height);
        }
    }

    public static class AreaCircle extends AreaShape{

        public AreaCircle(Location center, double radius, double height) {
            super(center, height, radius);
        }
    }

    public static class AreaRectangle extends AreaShape{

        private Location first, second;
        public AreaRectangle(Location firstCorner, Location secondCorner, double height) {
            super(LocationUtils.getMidPointLocation(firstCorner, secondCorner), height,
                    (firstCorner.distance(LocationUtils.getMidPointLocation(firstCorner, secondCorner))));

            this.first = firstCorner;
            this.second = secondCorner;
        }

        public Location getFirst() {
            return first;
        }

        public Location getSecond() {
            return second;
        }
    }


}
