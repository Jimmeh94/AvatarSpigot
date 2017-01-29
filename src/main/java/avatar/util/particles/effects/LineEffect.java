package avatar.util.particles.effects;

import avatar.util.misc.LocationUtils;
import org.bukkit.Location;

import java.util.List;

public class LineEffect extends AbstractEffect {
	
	private Location target;
	private List<Location> draw;
	private int display = 0;
	
	/**
	 * Displays a line of particles between 2 specified locations.
	 * @param target The end location of the line.
	 */
	public LineEffect(EffectData effectData, Location target) {
		super(effectData);
		this.target = target;
		draw = LocationUtils.getConnectingLine(effectData.getCenter(), target);
	}
	
	@Override
	public void play() {
		/*for(int i = 0; i <= display; i++){
			effectData.setDisplayAt(draw.get(i));
			playParticle();
		}
		if(display < draw.size() - 1)
			display++;*/
		Location  v = target.subtract(effectData.getCenter());
		for (double i = 0; i < v.length(); i += 0.5) {
			Location loc = effectData.getCenter().clone().add(v.clone().multiply(i));
			effectData.setDisplayAt(loc);
			playParticle();
		}
	}

}