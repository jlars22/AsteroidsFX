package dk.sdu.mmmi.cbse.common.debris;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.time.LocalTime;
import java.util.Random;

public class Debris extends Entity {

	private final LocalTime shouldBeRemovedTime;

	public Debris() {
		Random random = new Random();
		double size = random.nextDouble(0.5, 2);
		this.setPolygonCoordinates(2 * size, 0, 1 * size, 1 * size, 1 * size, 2 * size, 2 * size, 3 * size, 3 * size,
				3 * size, 4 * size, 2 * size, 4 * size, 1 * size, 3 * size, 0);
		shouldBeRemovedTime = LocalTime.now().plusSeconds(1);
	}

	public LocalTime getShouldBeRemovedTime() {
		return shouldBeRemovedTime;
	}
}
