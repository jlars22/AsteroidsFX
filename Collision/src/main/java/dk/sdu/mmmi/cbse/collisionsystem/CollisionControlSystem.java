package dk.sdu.mmmi.cbse.collisionsystem;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Entity.Type;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.debris.DebrisSPI;
import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.Collection;
import java.util.ServiceLoader;

public class CollisionControlSystem implements IPostEntityProcessingService {

	@Override
	public void process(GameData gameData, World world) {
		for (Entity entityA : world.getEntities()) {
			for (Entity entityB : world.getEntities()) {
				if (entityA.getID().equals(entityB.getID())) {
					continue;
				}
				if (checkCollision(entityA, entityB)) {
					handleCollision(entityA, entityB, world);
				}
			}
		}
	}

	private void handleCollision(Entity entityA, Entity entityB, World world) {
		handleEntityCollision(entityA, entityB, world, Type.ENEMY, this::handleEnemyCollision);
		handleEntityCollision(entityA, entityB, world, Type.PLAYER, this::handlePlayerCollision);
		handleEntityCollision(entityA, entityB, world, Type.ASTEROID, this::handleAsteroidCollision);
	}

	private void handleEntityCollision(Entity entityA, Entity entityB, World world, Type type,
			CollisionHandler handler) {
		if (type.equals(entityA.getType()) || type.equals(entityB.getType())) {
			if (type.equals(entityA.getType())) {
				handler.handle(entityA, entityB, world);
			} else {
				handler.handle(entityB, entityA, world);
			}
			removeBullet(entityA, entityB, world);
		}
	}

	private interface CollisionHandler {
		void handle(Entity entityA, Entity entityB, World world);
	}

	private void handleEnemyCollision(Entity enemy, Entity otherEntity, World world) {
		decrementHealthOrRemoveEntity(enemy, world);
		createDebris(enemy, world);
		if (otherEntity instanceof Bullet) {
			getScoreServices().stream().findFirst().ifPresent(scoreService -> scoreService.addScore(enemy));
		}
	}

	private void handlePlayerCollision(Entity player, Entity otherEntity, World world) {
		decrementHealthOrRemoveEntity(player, world);
		createDebris(player, world);
		if (player.getHealth() != 0) {
			world.removeEntity(player);
		}
	}

	private void handleAsteroidCollision(Entity asteroid, Entity otherEntity, World world) {
		decrementHealthOrRemoveEntity(asteroid, world);
		createDebris(asteroid, world);
		if (asteroid.getHealth() != 0) {
			splitAsteroid(asteroid, world);
		}
		if (otherEntity instanceof Bullet && ((Bullet) otherEntity).getOwner().getType().equals(Type.PLAYER)) {
			getScoreServices().stream().findFirst().ifPresent(scoreService -> scoreService.addScore(asteroid));
		}
	}

	private void removeBullet(Entity entityA, Entity entityB, World world) {
		if (entityA instanceof Bullet || entityB instanceof Bullet) {
			Entity bullet = entityA instanceof Bullet ? entityA : entityB;
			world.removeEntity(bullet);
		}
	}

	private void decrementHealthOrRemoveEntity(Entity entity, World world) {
		if (entity.getHealth() > 1) {
			entity.setHealth(entity.getHealth() - 1);
		} else {
			entity.setHealth(0);
			world.removeEntity(entity);
		}
	}

	private boolean checkCollision(Entity entityA, Entity entityB) {
		if (isEntitiesSameInstance(entityA, entityB))
			return false;
		if (isAnyEntityDebris(entityA, entityB))
			return false;

		double aLeft = entityA.getX();
		double aRight = entityA.getX() + entityA.getWidth();
		double aTop = entityA.getY();
		double aBottom = entityA.getY() + entityA.getHeight();

		double bLeft = entityB.getX();
		double bRight = entityB.getX() + entityB.getWidth();
		double bTop = entityB.getY();
		double bBottom = entityB.getY() + entityB.getHeight();

		return aLeft < bRight && aRight > bLeft && aTop < bBottom && aBottom > bTop;
	}

	private boolean isAnyEntityDebris(Entity entityA, Entity entityB) {
		return entityA.getClass().getSimpleName().equals("Debris")
				|| entityB.getClass().getSimpleName().equals("Debris");
	}

	private boolean isEntitiesSameInstance(Entity entityA, Entity entityB) {
		return entityA.getClass().equals(entityB.getClass());
	}

	private void splitAsteroid(Entity entity, World world) {
		getAsteroidSPIs().stream().findFirst().ifPresent(spi -> {
			spi.splitAsteroid(entity, world);
		});
	}

	private void createDebris(Entity entity, World world) {
		getDebrisSPIs().stream().findFirst().ifPresent(spi -> {
			spi.createDebris(entity, world);
		});
	}

	private Collection<? extends AsteroidSPI> getAsteroidSPIs() {
		return ServiceLoader.load(AsteroidSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

	private Collection<? extends IScoreService> getScoreServices() {
		return ServiceLoader.load(IScoreService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

	private Collection<? extends DebrisSPI> getDebrisSPIs() {
		return ServiceLoader.load(DebrisSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}
}
