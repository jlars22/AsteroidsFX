package dk.sdu.mmmi.cbse.common.debris;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.time.LocalTime;

public class Debris extends Entity {

	private final LocalTime shouldBeRemovedTime;

	public Debris() {
		super(Type.DEBRIS, new double[]{0, 0, 1, 0, 1, 1, 0, 1});
		shouldBeRemovedTime = LocalTime.now().plusSeconds(1);
	}

	public LocalTime getShouldBeRemovedTime() {
		return shouldBeRemovedTime;
	}
}
