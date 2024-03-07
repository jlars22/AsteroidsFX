package dk.sdu.mmmi.cbse.debrissystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.time.LocalTime;

public class Debris extends Entity {

	private final LocalTime shouldBeRemovedTime;

	public Debris(double size) {
		super(Type.DEBRIS,
				new double[]{10 * size, 0, 28 * size, 0, 40 * size, 10 * size, 45 * size, 20 * size, 40 * size,
						30 * size, 30 * size, 40 * size, 20 * size, 30 * size, 15 * size, 20 * size, 10 * size,
						10 * size, 10 * size, 0});
		shouldBeRemovedTime = LocalTime.now().plusSeconds(1);
	}

	public LocalTime getShouldBeRemovedTime() {
		return shouldBeRemovedTime;
	}
}
