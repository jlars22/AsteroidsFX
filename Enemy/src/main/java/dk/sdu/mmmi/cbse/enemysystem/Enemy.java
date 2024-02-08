package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

import java.time.LocalTime;

public class Enemy extends Entity {

    private LocalTime lastTimeSpawned;
    private LocalTime lastTimeFired;

    public Enemy() {
        this.setEntityType(EntityType.ENEMY);
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
}
