package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.EventBroker;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IObserver;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI, IObserver {

	private final double OFFSET_DISTANCE = 40;
	private final int BULLET_SPEED = 3;
	private final EventBroker eventBroker = EventBroker.getInstance();

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
	 * <p>
	 * Calculates the new position of the bullet using trigonometry based on its
	 * rotation and a constant speed.
	 * <p>
	 * Also tracks the total distance the bullet has travelled using the Pythagorean
	 * theorem
	 * <p>
	 * If the bullet exceeds its maximum travel distance, it is removed from the
	 * game world
	 *
	 * @param world
	 *            The game world.
	 * @param bullet
	 *            The bullet entity.
	 */
	private void handleTravel(World world, Bullet bullet) {
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
	public Entity createBullet(Entity shooter) {
		Bullet bullet = new Bullet();
		bullet.setRotation(shooter.getRotation());

		double offsetX = Math.cos(Math.toRadians(shooter.getRotation())) * OFFSET_DISTANCE;
		double offsetY = Math.sin(Math.toRadians(shooter.getRotation())) * OFFSET_DISTANCE;

		bullet.setX(shooter.getX() + offsetX);
		bullet.setY(shooter.getY() + offsetY);

		bullet.setOwner(shooter);
		return bullet;
	}

	@Override
	public Entity createBullet(Entity shooter, double rotation) {
		Bullet bullet = new Bullet();
		bullet.setRotation(rotation);

		double offsetX = Math.cos(Math.toRadians(bullet.getRotation())) * OFFSET_DISTANCE;
		double offsetY = Math.sin(Math.toRadians(bullet.getRotation())) * OFFSET_DISTANCE;

		bullet.setX(shooter.getX() + offsetX);
		bullet.setY(shooter.getY() + offsetY);

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

	@Override
	public void onEvent(Event event) {
		if (event.getEventType() == Event.EventType.COLLISION) {
			Entity entityA = event.getEntityA();
			Entity entityB = event.getEntityB();
			if (entityA instanceof Bullet) {
				event.getWorld().removeEntity(entityA);
				if (((Bullet) entityA).getOwner().getType() != Entity.Type.ENEMY) {
					Event scoreEvent = new Event(entityA, entityB, Event.EventType.SCORE_INCREMENT, event.getWorld());
					eventBroker.notifyObservers(scoreEvent);
				}
			}
			if (entityB instanceof Bullet) {
				event.getWorld().removeEntity(entityB);
				if (((Bullet) entityB).getOwner().getType() != Entity.Type.ENEMY) {
					Event scoreEvent = new Event(entityB, entityA, Event.EventType.SCORE_INCREMENT, event.getWorld());
					eventBroker.notifyObservers(scoreEvent);
				}
			}
		}
	}
}
