package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Player extends Entity {

    public Player() {
        this.setEntityType(EntityType.PLAYER);
        this.setPolygonCoordinates(-10,-10,20,0,-10,10);
        this.setColor("WHITE");
        this.setHealth(3);
    }

}
