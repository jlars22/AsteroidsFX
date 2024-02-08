package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Bullet extends Entity {

    private final double MAX_TRAVEL_DISTANCE = 400;
    private double distanceTravelled;

    public Bullet() {
        this.setEntityType(EntityType.BULLET);
        this.setPolygonCoordinates(2, 0, 1, 1, 1, 2, 2, 3, 3, 3, 4, 2, 4, 1, 3, 0);
        this.setColor("YELLOW");
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public double getMaxTravelDistance() {
        return MAX_TRAVEL_DISTANCE;
    }
}
