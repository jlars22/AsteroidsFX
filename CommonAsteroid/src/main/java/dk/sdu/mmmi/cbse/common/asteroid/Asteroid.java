package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
	private final int size; // 1: small, 2: medium, 3: large

	public Asteroid(int size) {
		super(Type.ASTEROID,
				new double[]{10 * size, 0, 28 * size, 0, 40 * size, 10 * size, 45 * size, 20 * size, 40 * size,
						30 * size, 30 * size, 40 * size, 20 * size, 30 * size, 15 * size, 20 * size, 10 * size,
						10 * size, 10 * size, 0});
		this.size = size;
		this.setHealth(size);
	}

	public int getSize() {
		return size;
	}

}
