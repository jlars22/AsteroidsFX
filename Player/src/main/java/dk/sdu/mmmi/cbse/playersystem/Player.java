package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

import java.time.LocalTime;

public class Player extends Entity {

    private LocalTime respawnTime;

    public Player() {
        this.setEntityType(EntityType.PLAYER);
        this.setPolygonCoordinates(-10,-10,20,0,-10,10);
        this.setColor("WHITE");
        this.setHealth(3);
    }

    public LocalTime getRespawnTime() {
        return respawnTime;
    }

    public void setRespawnTime(LocalTime respawnTime) {
        this.respawnTime = respawnTime;
    }
}
