package avatar.util.particles.effects;


import avatar.Avatar;
import avatar.util.misc.Vector;
import avatar.util.particles.effectData.EffectData;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public abstract class AbstractEffect {

	protected EffectData effectData;
	protected BukkitTask task;
	private long delay, interval;
	private int cancel;

	public AbstractEffect(EffectData effectData){
		this.effectData = effectData;
	}

	public AbstractEffect(EffectData effectData, long delay, long interval, int cancel){
		this.effectData = effectData;
		this.delay = delay;
		this.interval = interval;
		this.cancel = cancel;
	}

	/**
	 * An abstract method used to display the animation.
	 */
	public abstract void play();

	/**
	 * Starts the runnable, which makes the effect display itself every interval.
	 * 
	 * @return The current instance of the effect to allow chaining of methods.
	 */
	public AbstractEffect start() {
		task = Bukkit.getScheduler().runTaskTimer(Avatar.INSTANCE,

				new Runnable() {
					int c = 0;

					@Override
					public void run() {
						play();
						c++;
						if (c >= cancel)
							stop();
					}
				}
		, interval, delay);
		return this;
	}

	/**
	 * Stops the effect from automaticly displaying.
	 * 
	 * @return The current instance of the effect, to allow 'chaining' of
	 *         methods.
	 */
	public AbstractEffect stop() {
		if (task == null)
			return this;
		try {
			task.cancel();
		} catch (IllegalStateException exc) {
		}
		return this;
	}

	/**
	 * Spawns a particle using the set particle effect.
	 */
	protected void playParticle(){
		effectData.display();
	}

	public EffectData getEffectData() {
		return effectData;
	}

	protected Vector.Vector3D rotateAroundAxisX(Vector.Vector3D  v, double angle) {
		angle = Math.toRadians(angle);
		double y, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		y = v.getY() * cos - v.getZ() * sin;
		z = v.getY() * sin + v.getZ() * cos;
		return new Vector.Vector3D (v.getX(), y, z);
		//return v.set(y).setZ(z);
	}

	protected Vector.Vector3D  rotateAroundAxisY(Vector.Vector3D  v, double angle) {
		angle = Math.toRadians(angle);
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return new Vector.Vector3D (x, v.getY(), z);
		//return v.setX(x).setZ(z);
	}

	/*public float[] vectorToYawPitch(Vector3d v) {
		Location loc = new Location(null, 0, 0, 0);
		loc.setDirection(v);
		//return new float[] { loc.getYaw(), loc.getPitch() };
		return new float[] { v.getYaw(), loc.getPitch() };
	}*/

	public Vector.Vector3D  yawPitchToVector(float yaw, float pitch) {
		yaw += 90;
		return new Vector.Vector3D (Math.cos(Math.toRadians(yaw)), Math.sin(Math.toRadians(pitch)),
				Math.sin(Math.toRadians(yaw)));
	}

}