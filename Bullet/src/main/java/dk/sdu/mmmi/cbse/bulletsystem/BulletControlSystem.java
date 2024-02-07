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
        setStyleAndCoordinates(bullet, shooter);
        return bullet;
    }

    private void setStyleAndCoordinates(Entity entity, Entity shooter) {
        entity.setPolygonCoordinates(2, 0, 1, 1, 1, 2, 2, 3, 3, 3, 4, 2, 4, 1, 3, 0);
        entity.setRotation(shooter.getRotation());
        entity.setX(shooter.getX());
        entity.setY(shooter.getY());
        entity.setColor("YELLOW");
    }

}
