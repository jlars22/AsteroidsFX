package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private final int BULLET_SPEED = 3;

    @Override
    public void process(GameData gameData, World world) {

        // TODO: Set max range for bullet and make them continue through the boundaries until max range is reached
        for (Entity bullet : world.getEntities(Bullet.class)) {
            if (bullet.getX() > gameData.getDisplayWidth() + 10) {
                world.removeEntity(bullet);
            }

            if (bullet.getY() > gameData.getDisplayHeight() + 10) {
                world.removeEntity(bullet);
            }

            double changeX = Math.cos(Math.toRadians(bullet.getRotation())) * BULLET_SPEED;
            double changeY = Math.sin(Math.toRadians(bullet.getRotation())) * BULLET_SPEED;
            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);

        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        setStyle(bullet);
        bullet.setRotation(shooter.getRotation());
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        return bullet;
    }

    @Override
    public Entity createBulletRandomDirection(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        setStyle(bullet);
        bullet.setRotation(Math.random() * 360);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        return bullet;
    }

    private void setStyle(Entity entity) {
        entity.setPolygonCoordinates(2, 0, 1, 1, 1, 2, 2, 3, 3, 3, 4, 2, 4, 1, 3, 0);
        entity.setColor("YELLOW");
    }

}
