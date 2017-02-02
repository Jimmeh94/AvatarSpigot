package avatar.util.particles.effectData;

import org.bukkit.Particle;

/** -------- Display Profile ------- **/
public class DisplayProfile{

    public static DisplayProfileBuilder builder(){return new DisplayProfileBuilder();}

    private int amount = 10;
    protected Particle particle = Particle.FLAME;
    private double xOffset = 0, yOffset = 0, zOffset = 0;
    protected double displayAtXOffset;
    protected double displayAtYOffset;
    protected double displayAtZOffset;
    private boolean randomizeOffsets = false;
    private double velocity;
    private double extra = -1;
    private Cloneable data;

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
        extra = builder.extra;
        data = builder.data;
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

    public double getExtra() {
        return extra;
    }

    public Cloneable getData() {
        return data;
    }

    public void setData(Cloneable data) {
        this.data = data;
    }

    /**------------- Builder ------------- **/
    public static class DisplayProfileBuilder{
        private int amount = 10;
        protected Particle particle = Particle.FLAME;
        private double xOffset = 0, yOffset = 0, zOffset = 0;
        private double displayAtXOffset, displayAtYOffset, displayAtZOffset;
        private boolean randomizeOffsets = false;
        private double velocity;
        private double extra = -1;
        private Cloneable data;

        public DisplayProfileBuilder extra(double extra){
            this.extra = extra;
            return this;
        }

        public DisplayProfileBuilder data(Cloneable data){
            this.data = data;
            return this;
        }

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
