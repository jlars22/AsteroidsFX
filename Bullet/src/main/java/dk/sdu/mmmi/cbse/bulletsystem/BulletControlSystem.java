package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.Event.EventType;
import dk.sdu.mmmi.cbse.common.data.EventBroker;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import java.util.List;

public class BulletControlSystem implements IEntityProcessingService, IObserver {

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
		handleBulletCollision(event.getEntityA(), event.getEntityB(), event);
		handleBulletCollision(event.getEntityB(), event.getEntityA(), event);
	}

	@Override
	public List<EventType> getTopics() {
		return List.of(EventType.COLLISION);
	}

	private void handleBulletCollision(Entity entity, Entity otherEntity, Event event) {
		if (!(entity instanceof Bullet))
			return;

		event.getWorld().removeEntity(entity);
		if (((Bullet) entity).getOwner().getType() == Entity.Type.ENEMY)
			return;

		Event scoreEvent = new Event(entity, otherEntity, Event.EventType.SCORE_INCREMENT, event.getWorld(),
				event.getGameData());
		eventBroker.publish(scoreEvent);
	}
}
