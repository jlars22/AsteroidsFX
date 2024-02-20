package dk.sdu.mmmi.cbse.collisionsystem;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
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
					handleCollision(entityA, entityB, world, gameData);
				}
			}
		}
	}

	private void handleCollision(Entity entityA, Entity entityB, World world, GameData gameData) {
		handleEnemyCollision(entityA, entityB, world);
		handlePlayerCollision(entityA, entityB, world, gameData);
		handleAsteroidCollision(entityA, entityB, world);
	}

	private void handleEnemyCollision(Entity entityA, Entity entityB, World world) {
		// TODO: Implement enemy collision
	}

	private void handlePlayerCollision(Entity entityA, Entity entityB, World world, GameData gameData) {
		if (entityA.getEntityType().equals(EntityType.PLAYER) || entityB.getEntityType().equals(EntityType.PLAYER)) {
			if (entityA.getEntityType().equals(EntityType.PLAYER)) {
				decrementHealthOrRemoveEntity(entityA, world);
				if (entityA.getHealth() != 0) {
					world.removeEntity(entityA);
				}
			} else {
				decrementHealthOrRemoveEntity(entityB, world);
				if (entityB.getHealth() != 0) {
					world.removeEntity(entityB);
				}
			}
		}
	}

	private void handleAsteroidCollision(Entity entityA, Entity entityB, World world) {
		if (entityA.getEntityType().equals(EntityType.ASTEROID)
				|| entityB.getEntityType().equals(EntityType.ASTEROID)) {
			if (entityA.getEntityType().equals(EntityType.ASTEROID)) {
				decrementHealthOrRemoveEntity(entityA, world);
				if (entityA.getHealth() != 0) {
					splitAsteroid(entityA, world);
				}
			} else {
				decrementHealthOrRemoveEntity(entityB, world);
				if (entityB.getHealth() != 0) {
					splitAsteroid(entityB, world);
				}
			}
			removeBullet(entityA, entityB, world);
		}
	}

	private void removeBullet(Entity entityA, Entity entityB, World world) {
		if (entityA.getEntityType().equals(EntityType.BULLET) || entityB.getEntityType().equals(EntityType.BULLET)) {
			if (entityA.getEntityType().equals(EntityType.BULLET)) {
				world.removeEntity(entityA);
			} else {
				world.removeEntity(entityB);
			}
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
		if (isBulletFromOwner(entityA, entityB))
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

	private boolean isBulletFromOwner(Entity entityA, Entity entityB) {
		Entity bullet = entityA.getEntityType().equals(EntityType.BULLET) ? entityA : entityB;
		Entity otherEntity = entityA.getEntityType().equals(EntityType.BULLET) ? entityB : entityA;

		return bullet.getEntityType().equals(EntityType.BULLET)
				&& ((Bullet) bullet).getOwner().getID().equals(otherEntity.getID());
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
