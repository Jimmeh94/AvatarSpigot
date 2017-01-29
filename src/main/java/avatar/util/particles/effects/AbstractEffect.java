package avatar.util.particles.effects;


import avatar.Avatar;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.scheduler.Task;

public abstract class AbstractEffect {

	protected EffectData effectData;

	public AbstractEffect(EffectData effectData){
		this.effectData = effectData;
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
		Task task;
		Task.Builder taskBuilder = effectData.getTaskBuilder();
		task = taskBuilder.delayTicks(effectData.getDelay()).intervalTicks(effectData.getInterval()).execute(
				new Runnable() {
					int c = 0;

					@Override
					public void run() {
						play();
						c++;
						if (c >= effectData.getCancel() / effectData.getInterval())
							stop();
					}
				}
		).submit(Avatar.INSTANCE);
		effectData.setTask(task);
		return this;
	}

	/**
	 * Stops the effect from automaticly displaying.
	 * 
	 * @return The current instance of the effect, to allow 'chaining' of
	 *         methods.
	 */
	public AbstractEffect stop() {
		Task task = effectData.getTask();
		if (task == null)
			return this;
		try {
			task.cancel();
			task = null;
		} catch (IllegalStateException exc) {
		}
		return this;
	}

	/**
	 * Spawns a particle using the set particle effect.
	 */
	protected void playParticle(){
		effectData.getPlayParticles().playParticles(effectData, effectData.getDisplayAt());
	}

	public EffectData getEffectData() {
		return effectData;
	}

	protected Vector3d rotateAroundAxisX(Vector3d v, double angle) {
		angle = Math.toRadians(angle);
		double y, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		y = v.getY() * cos - v.getZ() * sin;
		z = v.getY() * sin + v.getZ() * cos;
		return new Vector3d(v.getX(), y, z);
		//return v.set(y).setZ(z);
	}

	protected Vector3d rotateAroundAxisY(Vector3d v, double angle) {
		angle = Math.toRadians(angle);
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return new Vector3d(x, v.getY(), z);
		//return v.setX(x).setZ(z);
	}

	/*public float[] vectorToYawPitch(Vector3d v) {
		Location loc = new Location(null, 0, 0, 0);
		loc.setDirection(v);
		//return new float[] { loc.getYaw(), loc.getPitch() };
		return new float[] { v.getYaw(), loc.getPitch() };
	}*/

	public Vector3d yawPitchToVector(float yaw, float pitch) {
		yaw += 90;
		return new Vector3d(Math.cos(Math.toRadians(yaw)), Math.sin(Math.toRadians(pitch)),
				Math.sin(Math.toRadians(yaw)));
	}

}