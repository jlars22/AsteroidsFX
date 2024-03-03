package dk.sdu.mmmi.cbse.enemysystem;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.Event.EventType;
import dk.sdu.mmmi.cbse.common.data.EventBroker;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

public class EnemyControlSystem implements IEntityProcessingService, IObserver, EnemySPI {
	private final EventBroker eventBroker = EventBroker.getInstance();
	private final Random random = new Random();

	@Override
	public void process(GameData gameData, World world) {
		handleRespawn(gameData, world);

		for (Entity entity : world.getEntities(Enemy.class)) {
			Enemy enemy = (Enemy) entity;
			startFiring(world, enemy);
			startMovement(enemy);
			handleBorders(gameData, enemy);
		}
	}

	@Override
	public void onEvent(Event event) {
		Entity entityA = event.getEntityA();
		Entity entityB = event.getEntityB();

		if (entityA instanceof Enemy) {
			if (entityB.getType() == Entity.Type.BULLET) {
				Event scoreEvent = new Event(entityA, entityB, Event.EventType.SCORE_INCREMENT, event.getWorld(),
						event.getGameData());
				eventBroker.publish(scoreEvent);
			}
			event.getWorld().removeEntity(entityA);
		}
		if (entityB instanceof Enemy) {
			if (entityB.getType() == Entity.Type.BULLET) {
				Event scoreEvent = new Event(entityA, entityB, Event.EventType.SCORE_INCREMENT, event.getWorld(),
						event.getGameData());
				eventBroker.publish(scoreEvent);
			}
			event.getWorld().removeEntity(entityB);
		}

	}

	@Override
	public EventType getTopic() {
		return EventType.COLLISION;
	}

	@Override
	public void resetEnemyPosition(GameData gameData, World world) {
		for (Entity enemy : world.getEntities(Enemy.class)) {
			setRandomPosition(gameData, (Enemy) enemy);
		}
	}

	private void handleRespawn(GameData gameData, World world) {
		int currentEnemyCount = world.getEntities(Enemy.class).size();

		double randomNumber = random.nextDouble();

		double spawnThreshold;
		if (currentEnemyCount == 0) {
			spawnThreshold = 0.0015;
		} else {
			spawnThreshold = 0.002 / ((currentEnemyCount * 10));
		}

		if (randomNumber < spawnThreshold) {
			Enemy enemy = createEnemyShip(gameData);
			world.addEntity(enemy);
		}
	}

	private void handleBorders(GameData gameData, Enemy enemy) {
		if (enemy.getX() < 0) {
			enemy.setX(gameData.getDisplayWidth());
		} else if (enemy.getX() > gameData.getDisplayWidth()) {
			enemy.setX(0);
		}

		if (enemy.getY() < 0) {
			enemy.setY(gameData.getDisplayHeight());
		} else if (enemy.getY() > gameData.getDisplayHeight()) {
			enemy.setY(0);
		}
	}

	private void startMovement(Enemy enemy) {
		double speed = 0.7;

		// If the enemy is not moving, set a random direction
		if (enemy.getDX() == 0 && enemy.getDY() == 0) {
			double direction = random.nextDouble(2 * Math.PI);
			enemy.setDX(Math.cos(direction) * speed);
			enemy.setDY(Math.sin(direction) * speed);
		}

		if (shouldChangeDirection(enemy)) {
			double newDirection = getNewDirection(enemy);

			enemy.setDX(Math.cos(newDirection) * speed);
			enemy.setDY(Math.sin(newDirection) * speed);
		}

		enemy.setX(enemy.getX() + enemy.getDX());
		enemy.setY(enemy.getY() + enemy.getDY());
	}

	private double getNewDirection(Enemy enemy) {
		double currentDirection = Math.atan2(enemy.getDY(), enemy.getDX());
		double adjustment = Math.toRadians(random.nextInt(-45, 45));
		return currentDirection + adjustment;
	}

	private boolean shouldChangeDirection(Enemy enemy) {
		LocalTime currentTime = LocalTime.now();
		LocalTime lastTimeChangedDirection = enemy.getLastTimeChangedDirection();

		if (lastTimeChangedDirection != null && !currentTime.isAfter(lastTimeChangedDirection.plusSeconds(3))) {
			return false;
		}

		enemy.setLastTimeChangedDirection(currentTime);
		return true;
	}

	private void startFiring(World world, Enemy enemy) {
		LocalTime currentTime = LocalTime.now();
		LocalTime lastTimeFired = enemy.getLastTimeFired();

		if (lastTimeFired == null || currentTime.isAfter(lastTimeFired.plusSeconds(3))) {
			getWeaponSPIs().stream().findFirst().ifPresent(spi -> spi.shoot(enemy, world));
			enemy.setLastTimeFired(currentTime);
		}
	}

	private Enemy createEnemyShip(GameData gameData) {
		Enemy enemyShip = new Enemy();
		setRandomPosition(gameData, enemyShip);
		return enemyShip;
	}

	private void setRandomPosition(GameData gameData, Enemy eneemy) {
		switch (random.nextInt(4)) {
			case 0 :
				eneemy.setX(0);
				eneemy.setY(random.nextInt(gameData.getDisplayHeight()));
				break;
			case 1 :
				eneemy.setX(gameData.getDisplayWidth());
				eneemy.setY(random.nextInt(gameData.getDisplayHeight()));
				break;
			case 2 :
				eneemy.setX(random.nextInt(gameData.getDisplayWidth()));
				eneemy.setY(0);
				break;
			case 3 :
				eneemy.setX(random.nextInt(gameData.getDisplayWidth()));
				eneemy.setY(gameData.getDisplayHeight());
				break;
		}
	}

	private Collection<? extends WeaponSPI> getWeaponSPIs() {
		return ServiceLoader.load(WeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

}
