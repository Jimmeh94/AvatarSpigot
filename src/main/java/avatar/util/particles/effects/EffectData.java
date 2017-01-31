package avatar.util.particles.effects;

import avatar.game.area.Area;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.util.misc.Vector;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitTask;

public class EffectData {

    public static EffectDataBuilder builder() {
        return new EffectDataBuilder();
    }

    private Location center, displayAt;
    private UserPlayer owner;
    private BukkitTask task;
    private long delay, interval;
    private int cancel; //cancel is how many intervals have passed
    private IPlayParticles playParticles;
    private boolean randomizeOffsets;
    private double displayRadius;
    private Area displayArea;
    private DisplayProfile[] displayProfiles;
    private DisplayProfile activeDisplayProfile;

    private EffectData(EffectDataBuilder builder){
        this.center = builder.center;
        this.displayAt = builder.center.clone();
        this.owner = builder.owner;
        this.delay = builder.delay;
        this.interval = builder.interval;
        this.cancel = builder.cancel;
        this.playParticles = builder.playParticles;
        this.displayRadius = builder.displayRadius;
        this.displayArea = builder.displayArea;
        this.displayProfiles = builder.displayProfiles;
    }

    public void setActiveDisplayProfile(DisplayProfile activeDisplayProfile) {
        this.activeDisplayProfile = activeDisplayProfile;
    }

    public DisplayProfile getActiveDisplayProfile() {
        return activeDisplayProfile;
    }

    public DisplayProfile[] getDisplayProfiles() {
        return displayProfiles;
    }

    public EffectData setDisplayAt(Location displayAt){
        this.displayAt = displayAt;
        return this;
    }

    public EffectData setBukkitTask(BukkitTask task) {
        this.task = task;
        return this;
    }

    public EffectData setCenter(Location center) {
        this.center = center;
        return this;
    }

    public void setRandomizeOffsets(boolean randomizeOffsets) {
        this.randomizeOffsets = randomizeOffsets;
    }

    public EffectData setLocation(Location location) {
        this.center = location;
        return this;
    }

    public boolean isRandomizeOffsets() {
        return randomizeOffsets;
    }

    public EffectData setPlayParticles(IPlayParticles playParticles) {
        this.playParticles = playParticles;
        return this;
    }

    public Location getCenter() {
        return center;
    }

    public User getOwner() {
        return owner;
    }

    public BukkitTask getBukkitTask() {
        return task;
    }

    public long getDelay() {
        return delay;
    }

    public long getInterval() {
        return interval;
    }

    public int getCancel() {
        return cancel;
    }

    public IPlayParticles getPlayParticles() {
        return playParticles;
    }

    public Location getDisplayAt() {
        if(displayAt == null)
            displayAt = center.clone();
        return displayAt;
    }

    public Area getDisplayArea() {
        return displayArea;
    }

    public double getDisplayRadius() {
        return displayRadius;
    }

    public void adjustDisplayProfile(Vector.Vector3D vector3D) {
        activeDisplayProfile.displayAtXOffset = vector3D.getX();
        activeDisplayProfile.displayAtYOffset = vector3D.getY();
        activeDisplayProfile.displayAtZOffset = vector3D.getZ();
    }

    /** -------- Builder -------- **/
    public static class EffectDataBuilder{
        private Location center;
        private UserPlayer owner;
        private long delay, interval;
        private int cancel; //cancel is how many intervals have passed
        private IPlayParticles playParticles;
        private double displayRadius;
        private Area displayArea;
        private DisplayProfile[] displayProfiles;

        public EffectDataBuilder displayRadius(double displayRadius){
            this.displayRadius = displayRadius;
            return this;
        }

        public EffectDataBuilder displayArea(Area displayArea){
            this.displayArea = displayArea;
            return this;
        }

        public EffectDataBuilder taskInfo(long delay, long interval, int cancel){
            this.delay = delay;
            this.interval = interval;
            this.cancel = cancel;
            return this;
        }

        public EffectDataBuilder center(Location center){
            this.center = center;
            return this;
        }

        public EffectDataBuilder user(UserPlayer owner){
            this.owner = owner;
            return this;
        }

        public EffectData build(){
            return new EffectData(this);
        }

        public EffectDataBuilder displayProfiles(DisplayProfile... displayProfiles){
            this.displayProfiles = displayProfiles;
            return this;
        }

        public EffectDataBuilder playParticles(IPlayParticles iPlayParticles){
            this.playParticles = iPlayParticles;
            return this;
        }
    }

    /** -------- Display Profile ------- **/
    public static class DisplayProfile{

        public static DisplayProfileBuilder builder(){return new DisplayProfileBuilder();}

        private int amount = 10;
        protected Particle particle = Particle.FLAME;
        private double xOffset = 0, yOffset = 0, zOffset = 0;
        private double displayAtXOffset, displayAtYOffset, displayAtZOffset;
        private boolean randomizeOffsets = false;
        private double velocity;

        public DisplayProfile(DisplayProfileBuilder builder){
            this.amount = builder.amount;
            this.particle = builder.particle;
            this.xOffset = builder.xOffset;
            this.yOffset = builder.yOffset;
            this.zOffset = builder.zOffset;
            this.displayAtXOffset = builder.displayAtXOffset;
            this.displayAtYOffset = builder.displayAtYOffset;
            this.displayAtZOffset = builder.displayAtZOffset;
            this.randomizeOffsets = builder.randomizeOffsets;
            this.velocity = builder.velocity;
        }

        public void setyOffset(double yOffset) {
            this.yOffset = yOffset;
        }

        public void setzOffset(double zOffset) {
            this.zOffset = zOffset;
        }

        public void setDisplayAtXOffset(double displayAtXOffset) {
            this.displayAtXOffset = displayAtXOffset;
        }

        public void setDisplayAtYOffset(double displayAtYOffset) {
            this.displayAtYOffset = displayAtYOffset;
        }

        public void setDisplayAtZOffset(double displayAtZOffset) {
            this.displayAtZOffset = displayAtZOffset;
        }

        public void setxOffset(double xOffset) {
            this.xOffset = xOffset;
        }

        public int getAmount() {
            return amount;
        }

        public Particle getParticle() {
            return particle;
        }

        public double getxOffset() {
            return xOffset;
        }

        public double getyOffset() {
            return yOffset;
        }

        public double getzOffset() {
            return zOffset;
        }

        public double getDisplayAtXOffset() {
            return displayAtXOffset;
        }

        public double getDisplayAtYOffset() {
            return displayAtYOffset;
        }

        public double getDisplayAtZOffset() {
            return displayAtZOffset;
        }

        public boolean isRandomizeOffsets() {
            return randomizeOffsets;
        }

        public double getVelocity() {
            return velocity;
        }

        /**------------- Builder ------------- **/
        public static class DisplayProfileBuilder{
            private int amount = 10;
            protected Particle particle = Particle.FLAME;
            private double xOffset = 0, yOffset = 0, zOffset = 0;
            private double displayAtXOffset, displayAtYOffset, displayAtZOffset;
            private boolean randomizeOffsets = false;
            private double velocity;

            public DisplayProfileBuilder amount(int amount){
                this.amount = amount;
                return this;
            }

            public DisplayProfileBuilder particle(Particle particle){
                this.particle = particle;
                return this;
            }

            public DisplayProfileBuilder particleOffsets(double x, double y, double z){
                this.xOffset = x;
                this.yOffset = y;
                this.zOffset = z;
                return this;
            }

            public DisplayProfileBuilder displayLocationOffsets(double x, double y, double z){
                this.displayAtXOffset = x;
                this.displayAtYOffset = y;
                this.displayAtZOffset = z;
                return this;
            }

            public DisplayProfileBuilder randomizeOffsets(boolean a){
                randomizeOffsets = a;
                return this;
            }

            public DisplayProfileBuilder velocity(double v){
                this.velocity = v;
                return this;
            }

            public DisplayProfile build(){return new DisplayProfile(this);}

        }
    }
}
