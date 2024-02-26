package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService, IObserver {

	private final Random random = new Random();
	@Override
	public void process(GameData gameData, World world) {
		for (Entity entity : world.getEntities(Asteroid.class)) {
			Asteroid asteroid = (Asteroid) entity;
			handleTravel(asteroid);
			handleBorders(gameData, asteroid);
		}
	}

	private void handleTravel(Asteroid asteroid) {
		asteroid.setX(asteroid.getX() + asteroid.getDX());
		asteroid.setY(asteroid.getY() + asteroid.getDY());
	}

	private void handleBorders(GameData gameData, Asteroid asteroid) {
		double asteroidWidth = asteroid.getWidth();
		double asteroidHeight = asteroid.getHeight();

		if (asteroid.getX() < -asteroidWidth) {
			asteroid.setX(gameData.getDisplayWidth());
		} else if (asteroid.getX() > gameData.getDisplayWidth()) {
			asteroid.setX(-asteroidWidth);
		}

		if (asteroid.getY() < -asteroidHeight) {
			asteroid.setY(gameData.getDisplayHeight());
		} else if (asteroid.getY() > gameData.getDisplayHeight()) {
			asteroid.setY(-asteroidHeight);
		}
	}

	@Override
	public void onEvent(Event event) {
		if (event.getEventType() == Event.EventType.COLLISION) {
			Entity entityA = event.getEntityA();
			Entity entityB = event.getEntityB();
			if (entityA instanceof Asteroid && entityB instanceof Asteroid) {
				return;
			}
			if (entityA instanceof Asteroid) {
				splitAsteroid(entityA, event.getWorld());
			}
			if (entityB instanceof Asteroid) {
				splitAsteroid(entityB, event.getWorld());
			}
		}
	}

	private void splitAsteroid(Entity entity, World world) {
		Asteroid asteroid = (Asteroid) entity;
		entity.setHealth(entity.getHealth() - 1);
		world.removeEntity(asteroid);
		if (asteroid.getHealth() == 0) {
			return;
		}
		for (int i = 0; i < 2; i++) {
			Asteroid newAsteroid = new Asteroid(asteroid.getSize() - 1);
			newAsteroid.setX(asteroid.getX());
			newAsteroid.setY(asteroid.getY());
			newAsteroid.setDX(random.nextDouble(-1, 1));
			newAsteroid.setDY(random.nextDouble(-1, 1));
			newAsteroid.setRotation(random.nextDouble(360));
			world.addEntity(newAsteroid);
		}
	}
}
