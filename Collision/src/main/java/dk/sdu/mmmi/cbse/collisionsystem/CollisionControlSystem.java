package dk.sdu.mmmi.cbse.collisionsystem;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Entity.EntityType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
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
		handleEntityCollision(entityA, entityB, world, EntityType.ENEMY, this::handleEnemyCollision);
		handleEntityCollision(entityA, entityB, world, EntityType.PLAYER, this::handlePlayerCollision);
		handleEntityCollision(entityA, entityB, world, EntityType.ASTEROID, this::handleAsteroidCollision);
	}

	private void handleEntityCollision(Entity entityA, Entity entityB, World world, EntityType type,
			CollisionHandler handler) {
		if (entityA.getEntityType().equals(type) || entityB.getEntityType().equals(type)) {
			if (entityA.getEntityType().equals(type)) {
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
	}

	private void handlePlayerCollision(Entity player, Entity otherEntity, World world) {
		decrementHealthOrRemoveEntity(player, world);
		if (player.getHealth() != 0) {
			world.removeEntity(player);
		}
	}

	private void handleAsteroidCollision(Entity asteroid, Entity otherEntity, World world) {
		decrementHealthOrRemoveEntity(asteroid, world);
		if (asteroid.getHealth() != 0) {
			splitAsteroid(asteroid, world);
		}
	}

	private void removeBullet(Entity entityA, Entity entityB, World world) {
		if (entityA.getEntityType().equals(EntityType.BULLET) || entityB.getEntityType().equals(EntityType.BULLET)) {
			Entity bullet = entityA.getEntityType().equals(EntityType.BULLET) ? entityA : entityB;
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
		if (isEntitiesSameType(entityA, entityB))
			return false;

		double aLeft = entityA.getX();
		double aRight = entityA.getX() + entityA.getWidth();
		double aTop = entityA.getY();
		double aBottom = entityA.getY() + entityA.getHeight();

		double bLeft = entityB.getX();
		double bRight = entityB.getX() + entityB.getWidth();
		double bTop = entityB.getY();
		double bBottom = entityB.getY() + entityB.getHeight();

		// Check if the bounding boxes overlap
		return aLeft < bRight && aRight > bLeft && aTop < bBottom && aBottom > bTop;
	}

	private boolean isEntitiesSameType(Entity entityA, Entity entityB) {
		return entityA.getEntityType().equals(entityB.getEntityType());
	}

	private void splitAsteroid(Entity entity, World world) {
		getAsteroidSPIs().stream().findFirst().ifPresent(spi -> {
			spi.splitAsteroid(entity, world);
		});
	}

	private Collection<? extends AsteroidSPI> getAsteroidSPIs() {
		return ServiceLoader.load(AsteroidSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}
}
