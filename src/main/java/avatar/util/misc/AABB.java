package avatar.util.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.annotation.Nullable;

public class AABB {
    private final Location min;
    private final Location max;
    @Nullable
    private Vector.Vector3D size;
    @Nullable
    private Location center;

    public AABB(Location firstCorner, Location secondCorner) {
       this(firstCorner.getX(), firstCorner.getY(), firstCorner.getZ(), secondCorner.getX(), secondCorner.getY(), secondCorner.getZ());
    }

    public AABB(double x1, double y1, double z1, double x2, double y2, double z2){
        min = new Location(Bukkit.getWorlds().get(0), x1, y1, z1);
        max = new Location(Bukkit.getWorlds().get(0), x2, y2, z2);
        this.center = LocationUtils.getMidPointLocation(min, max);
        size = new Vector.Vector3D(LocationUtils.getDistance(min.getX(), max.getX()),
                LocationUtils.getDistance(min.getY(), max.getY()), LocationUtils.getDistance(min.getZ(), max.getZ()));
    }

    public Location getMin() {
        return this.min;
    }

    public Location getMax() {
        return this.max;
    }

    public Location getCenter() {
        return this.center;
    }

    public Vector.Vector3D getSize() {
        return this.size;
    }

    public boolean contains(Location point) {
        return this.contains(point.getX(), point.getY(), point.getZ());
    }

    public boolean contains(double x, double y, double z) {
        return this.min.getX() <= x && this.max.getX() >= x && this.min.getY() <= y && this.max.getY() >= y && this.min.getZ() <= z && this.max.getZ() >= z;
    }

    public boolean intersects(AABB other) {
        return this.max.getX() >= other.getMin().getX() && other.getMax().getX() >= this.min.getX() && this.max.getY() >= other.getMin().getY() && other.getMax().getY() >= this.min.getY() && this.max.getZ() >= other.getMin().getZ() && other.getMax().getZ() >= this.min.getZ();
    }

    public AABB offset(Vector.Vector3D offset) {
        return this.offset(offset.getX(), offset.getY(), offset.getZ());
    }

    public AABB offset(double x, double y, double z) {
        return new AABB(this.min.add(x, y, z), this.max.add(x, y, z));
    }

    public AABB expand(Vector.Vector3D amount) {
        return this.expand(amount.getX(), amount.getY(), amount.getZ());
    }

    public AABB expand(double x, double y, double z) {
        x /= 2.0D;
        y /= 2.0D;
        z /= 2.0D;
        return new AABB(this.min.subtract(x, y, z), this.max.add(x, y, z));
    }

    public boolean equals(Object other) {
        if(this == other) {
            return true;
        } else if(!(other instanceof AABB)) {
            return false;
        } else {
            AABB aabb = (AABB)other;
            return this.min.equals(aabb.min) && this.max.equals(aabb.max);
        }
    }

    public int hashCode() {
        int result = this.min.hashCode();
        result = 31 * result + this.max.hashCode();
        return result;
    }

    public String toString() {
        return "AABB(" + this.min + " to " + this.max + ")";
    }
}

