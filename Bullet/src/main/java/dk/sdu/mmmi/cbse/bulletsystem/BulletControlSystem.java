package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private final int BULLET_SPEED = 5;

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            if (bullet.getX() > gameData.getDisplayWidth()) {
                world.removeEntity(bullet);
            }
            if (bullet.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }

            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);

        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        setShape(bullet);
        bullet.setRotation(shooter.getRotation());
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        System.out.println("Created bullet" + bullet);
        return bullet;
    }

    private void setShape(Entity entity) {
        entity.setPolygonCoordinates(-20, -20, 10, 0, -20, 20);
    }

}
