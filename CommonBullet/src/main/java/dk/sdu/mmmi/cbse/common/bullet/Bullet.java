package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Bullet extends Entity {

	private double distanceTravelled;
	private Entity owner;

	public Bullet() {
		this.setPolygonCoordinates(2, 0, 1, 1, 1, 2, 2, 3, 3, 3, 4, 2, 4, 1, 3, 0);
	}

	public double getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(double distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public double getMaxTravelDistance() {
		return 400;
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}
}
