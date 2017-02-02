package avatar.util.particles.effectData;

import avatar.util.directional.Vector;
import org.bukkit.Location;

public abstract class EffectData {

    protected Location center, displayAt;
    protected DisplayProfile[] displayProfiles;
    protected DisplayProfile activeDisplayProfile;

    public abstract void display();

    public EffectData(Location center, DisplayProfile... displayProfiles){
        this(center, center, displayProfiles);
    }

    public EffectData(Location center, Location displayAt, DisplayProfile... displayProfiles){
        this.center = center.clone();
        this.displayAt = displayAt.clone();
        this.displayProfiles = displayProfiles;
        activeDisplayProfile = displayProfiles[0];
    }

    public DisplayProfile[] getDisplayProfiles() {
        return displayProfiles;
    }

    public void setActiveDisplayProfile(DisplayProfile activeDisplayProfile) {
        this.activeDisplayProfile = activeDisplayProfile;
    }

    public void adjustDisplayProfile(Vector.Vector3D vector3D) {
        activeDisplayProfile.displayAtXOffset = vector3D.getX();
        activeDisplayProfile.displayAtYOffset = vector3D.getY();
        activeDisplayProfile.displayAtZOffset = vector3D.getZ();
    }

    public Location getCenter() {
        return center;
    }

    public Location getDisplayAt() {
        return displayAt;
    }

    public DisplayProfile getActiveDisplayProfile() {
        return activeDisplayProfile;
    }

    public void setCenter(Location center) {
        this.center = center;
    }

    public void setDisplayAt(Location displayAt) {
        this.displayAt = displayAt;
    }
}
