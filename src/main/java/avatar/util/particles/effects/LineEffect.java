package avatar.util.particles.effects;

import avatar.util.directional.LocationUtils;
import avatar.util.directional.Vector;
import avatar.util.particles.effectData.EffectData;
import org.bukkit.Location;

public class LineEffect extends AbstractEffect {

    private Location target;

    public LineEffect(EffectData effectData, Location target) {
        super(effectData);

        this.target = target;
    }

    public LineEffect(EffectData effectData, Location target, long delay, long interval, int cancel){
        super(effectData, delay, interval, cancel);

        this.target = target;
    }

    @Override
    public void play() {
        Vector.Vector3D offset = LocationUtils.getOffsetBetween(effectData.getCenter(), target);
        offset.setX((offset.getX() / Math.abs(offset.getX())) / 10);
        offset.setY((offset.getY() / Math.abs(offset.getY())) / 10);
        offset.setZ((offset.getZ() / Math.abs(offset.getZ())) / 10);
        //now we have -.1 or .1 as the offsets

        //this will draw a particle in 10 locations inside each block
        for(Location at: LocationUtils.getConnectingLine(effectData.getCenter(), target)){
            for(int i = 0; i < 10; i++){
                effectData.setDisplayAt(at.clone().add(offset.getX(), offset.getY(), offset.getZ()));
                playParticle();
            }
        }
    }
}
