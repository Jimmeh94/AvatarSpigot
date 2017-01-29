package avatar.util.particles.effects;

public class HelixEffect extends AbstractEffect {

	private double[][][] coordinates;
	private int lines;
	private int spinner;
	private int circleCoordinates;

	public HelixEffect(EffectData effectData, double top, double heightStep, double radius, int lines) {
		super(effectData);

		init(top, heightStep, radius);
		setLines(lines);
	}

	private void init(double height, double heightStep, double radius) {
		circleCoordinates = 120;
		coordinates = new double[(int) (height / heightStep) + 1][][];
		for (int i = 0; i < height / heightStep; i++) {
			double y = heightStep * i;
			coordinates[i] = new double[circleCoordinates][];
			int i2 = 0;
			for (double a = 0; a < Math.PI * 2; a += Math.PI / (circleCoordinates / 2)) {
				double x = Math.cos(a) * radius;
				double z = Math.sin(a) * radius;
				coordinates[i][i2++] = new double[] { x, y, z };
			}
		}
	}

	@Override
	public void play() {
		int i = 0;
		spinner++;
		for (double[][] array2d : coordinates) {
			if (array2d == null) continue;
			for (int line = 0; line < lines; line++) {
				int stepPerLine = circleCoordinates / lines;
				double[] array = array2d[(((stepPerLine * line) % circleCoordinates) + (i * 2 % circleCoordinates)
						+ (circleCoordinates - 1 - ((spinner) % circleCoordinates))) % circleCoordinates];
				effectData.setDisplayAt(effectData.getCenter().add(array[0], array[1], array[2]));
				playParticle();
				effectData.getCenter().sub(array[0], array[1], array[2]);
			}
			i++;
		}
	}

	public HelixEffect setLines(int i) {
		lines = i;
		return this;
	}

}