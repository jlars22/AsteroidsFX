package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.EventBroker;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionControlSystem implements IPostEntityProcessingService {
	private final EventBroker eventBroker = EventBroker.getInstance();

	@Override
	public void process(GameData gameData, World world) {
		for (Entity entityA : world.getEntities()) {
			for (Entity entityB : world.getEntities()) {
				if (entityA.getID().equals(entityB.getID())) {
					continue;
				}
				if (checkCollision(entityA, entityB)) {
					Event event = new Event(entityA, entityB, Event.EventType.COLLISION, world);
					eventBroker.notifyObservers(event);
				}
			}
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

}
