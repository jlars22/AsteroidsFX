package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService, BulletSPI {

    @Override
	public void start(GameData gameData, World world) {
	}

	@Override
	public void stop(GameData gameData, World world) {
		for (Entity e : world.getEntities()) {
			if (e.getClass() == Bullet.class) {
				world.removeEntity(e);
			}
		}
	}

	@Override
	public Entity createBullet(Entity shooter, double rotation) {
		Bullet bullet = new Bullet();
		bullet.setRotation(rotation);

        double OFFSET_DISTANCE = 40;
        double offsetX = Math.cos(Math.toRadians(bullet.getRotation())) * OFFSET_DISTANCE;
		double offsetY = Math.sin(Math.toRadians(bullet.getRotation())) * OFFSET_DISTANCE;

		bullet.setX(shooter.getX() + offsetX);
		bullet.setY(shooter.getY() + offsetY);

		bullet.setOwner(shooter);
		return bullet;
	}
}
