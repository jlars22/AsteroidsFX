package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.time.LocalTime;

public class Enemy extends Entity {

	private LocalTime lastTimeSpawned;
	private LocalTime lastTimeFired;
	private LocalTime lastTimeChangedDirection;

	public Enemy() {
		this.setPolygonCoordinates(12, -1, 8, -1, 8, -3, 6, -3, 6, -5, -2, -5, -2, -7, 0, -7, 0, -9, -10, -9, -10, -5,
				-8, -5, -8, -3, -6, -3, -6, -1, -10, -1, -10, 1, -6, 1, -6, 3, -8, 3, -8, 5, -10, 5, -10, 9, 0, 9, 0, 7,
				-2, 7, -2, 5, 2, 5, 2, 1, 4, 1, 4, -1, 2, -1, 2, -3, 4, -3, 4, -1, 6, -1, 6, 1, 4, 1, 4, 3, 2, 3, 2, 5,
				6, 5, 6, 3, 8, 3, 8, 1, 12, 1);
		this.setHealth(1);
		this.setRotation(-90);
	}

	public LocalTime getLastTimeFired() {
		return lastTimeFired;
	}

	public void setLastTimeFired(LocalTime lastTimeFired) {
		this.lastTimeFired = lastTimeFired;
	}

	public LocalTime getLastTimeChangedDirection() {
		return lastTimeChangedDirection;
	}

	public void setLastTimeChangedDirection(LocalTime lastTimeChangedDirection) {
		this.lastTimeChangedDirection = lastTimeChangedDirection;
	}
}
