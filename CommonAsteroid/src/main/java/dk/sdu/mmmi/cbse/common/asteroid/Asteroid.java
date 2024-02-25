package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
	private int size; // 1: small, 2: medium, 3: large

	public Asteroid(int size) {
		this.size = size;
		this.setHealth(size);
		this.setPolygonCoordinates(10 * size, 0, 28 * size, 0, 40 * size, 10 * size, 45 * size, 20 * size, 40 * size,
				30 * size, 30 * size, 40 * size, 20 * size, 30 * size, 15 * size, 20 * size, 10 * size, 10 * size,
				10 * size, 0);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
