package avatar.game.area;

import avatar.events.custom.AreaEvent;
import avatar.game.chat.channel.ChatChannel;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.manager.AbilityManager;
import avatar.manager.ListenerManager;
import avatar.util.directional.LocationUtils;
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
    private ChatChannel chatChannel;
    private AbilityManager abilityManager;

    public Area(AreaReferences reference){
        this.shape = reference.getShape();
        this.displayName = reference.getDisplayName();
        this.chatChannel = reference.getChatChannel();
        this.reference = reference;
        abilityManager = new AbilityManager();

        if(reference.getChildren() != null) {
            for (AreaReferences r : reference.getChildren()) {
                children.add(new Area(r));
            }
        }
    }

    public AbilityManager getAbilityManager() {
        return abilityManager;
    }

    public boolean hasChild(AreaReferences reference){
        for(Area area: children){
            if(area.is(reference))
                return true;
        }
        return false;
    }

    public Optional<Area> getChild(AreaReferences reference){
        for(Area area: children){
            if(area.is(reference))
                return Optional.of(area);
        }
        return Optional.empty();
    }

    public Location getCenter(){return shape.getCenter();}

    public boolean is(AreaReferences reference){return this.reference == reference;}

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

    public boolean contains(Location location){

        //if this isn't a global area
        if(shape != null){
            return shape.contains(location);
        }

        //means this is a global area
        return true;
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

    public AreaShape getShape() {
        return shape;
    }

    public void tick() {
        abilityManager.tick();
        for(Area area: children){
            area.tick();
        }
    }

    public static abstract class AreaShape {
        protected Location center;
        protected double height, radius;

        //For quest checkpoint objectives of reaching an area
        protected List<Location> outline;

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

        public abstract boolean contains(Location location);

        protected abstract void generateOutline();

        public List<Location> getOutline() {
            return outline;
        }
    }

    public static class AreaCircle extends AreaShape{

        public AreaCircle(Location center, double radius, double height) {
            super(center, height, radius);

            generateOutline();
        }

        @Override
        public boolean contains(Location location) {
            double x1 = location.getX(), x2 = center.getX();
            double z1 = location.getZ(), z2 = center.getZ();
            double y = location.getY();

            return LocationUtils.getDistance(x1, x2) <= radius && LocationUtils.getDistance(z1, z2) <= radius
                    && y >= center.getY() && y <= center.getY() + getHeight();
        }

        @Override
        protected void generateOutline() {
            outline = LocationUtils.getCircleOutline(center, radius, false);
        }
    }

    public static class AreaRectangle extends AreaShape{

        private Location first, second;
        public AreaRectangle(Location firstCorner, Location secondCorner, double height) {
            super(LocationUtils.getMidPointLocation(firstCorner, secondCorner), height,
                    (firstCorner.distance(LocationUtils.getMidPointLocation(firstCorner, secondCorner))));

            this.first = firstCorner;
            this.second = secondCorner;
            generateOutline();
        }

        public Location getFirst() {
            return first;
        }

        public Location getSecond() {
            return second;
        }

        @Override
        public boolean contains(Location location) {
            double xMax = Math.max(first.getX(), second.getX());
            double xMin = Math.min(first.getX(), second.getX());
            double zMax = Math.max(first.getZ(), second.getZ());
            double zMin = Math.min(first.getZ(), second.getZ());

            return xMin <= location.getX() && xMax >= location.getX() &&
                    zMin <= location.getZ() && zMax >= location.getZ() &&
                    center.getY() <= location.getY() && center.getY() + height >= location.getY();
        }

        @Override
        protected void generateOutline() {
            outline = LocationUtils.getSquareOutline(first, second);
        }
    }


}
