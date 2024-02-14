package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Bullet.class)) {
            Bullet bullet = (Bullet) entity;
            handleTravel(world, bullet);
            handleBorders(gameData, bullet);
        }
    }

    /**
     * Manages bullet movement in the game world
     *
     * <p>Calculates the new position of the bullet using trigonometry based on its rotation and a constant speed
     * Also tracks the total distance the bullet has travelled using the Pythagorean theorem
     * If the bullet exceeds its maximum travel distance, it is removed from the game world
     *
     * @param world  The game world.
     * @param bullet The bullet entity.
     */
    private void handleTravel(World world, Bullet bullet) {
        int BULLET_SPEED = 3;
        double changeX = Math.cos(Math.toRadians(bullet.getRotation())) * BULLET_SPEED;
        double changeY = Math.sin(Math.toRadians(bullet.getRotation())) * BULLET_SPEED;

        bullet.setX(bullet.getX() + changeX);
        bullet.setY(bullet.getY() + changeY);

        double distanceTravelled = bullet.getDistanceTravelled() + Math.hypot(changeX, changeY);
        bullet.setDistanceTravelled(distanceTravelled);

        if (distanceTravelled > bullet.getMaxTravelDistance()) {
            world.removeEntity(bullet);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet();
        bullet.setRotation(shooter.getRotation());
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setOwner(shooter);
        return bullet;
    }

    @Override
    public Entity createBulletRandomDirection(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet();
        bullet.setRotation(Math.random() * 360);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setOwner(shooter);
        return bullet;
    }

    private void handleBorders(GameData gameData, Bullet bullet) {
        if (bullet.getX() < 0) {
            bullet.setX(gameData.getDisplayWidth());
        } else if (bullet.getX() > gameData.getDisplayWidth()) {
            bullet.setX(0);
        }

        if (bullet.getY() < 0) {
            bullet.setY(gameData.getDisplayHeight());
        } else if (bullet.getY() > gameData.getDisplayHeight()) {
            bullet.setY(0);
        }
    }

}
