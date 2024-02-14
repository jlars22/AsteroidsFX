package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

import java.time.LocalTime;

public class Enemy extends Entity {

    private LocalTime lastTimeSpawned;
    private LocalTime lastTimeFired;
    private LocalTime lastTimeChangedDirection;

    public Enemy() {
        this.setEntityType(EntityType.ENEMY);
        this.setPolygonCoordinates(0, -10, -7, -3, -7, 3, 0, 10, 7, 3, 7, -3);
        this.setColor("RED");
        this.setHealth(1);
    }

    public LocalTime getLastTimeSpawned() {
        return lastTimeSpawned;
    }

    public void setLastTimeSpawned(LocalTime lastTimeSpawned) {
        this.lastTimeSpawned = lastTimeSpawned;
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
