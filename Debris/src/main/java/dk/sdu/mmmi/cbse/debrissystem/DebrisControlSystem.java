package dk.sdu.mmmi.cbse.debrissystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.debris.Debris;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import java.time.LocalTime;
import java.util.Random;

public class DebrisControlSystem implements IEntityProcessingService, IObserver {

	private final Random random = new Random();
	@Override
	public void process(GameData gameData, World world) {
		for (Entity entity : world.getEntities(Debris.class)) {
			Debris debris = (Debris) entity;
			if (debris.getShouldBeRemovedTime().isBefore(LocalTime.now())) {
				world.removeEntity(debris);
			}
			debris.setX(debris.getX() + debris.getDX());
			debris.setY(debris.getY() + debris.getDY());
		}

	}

	@Override
	public void onEvent(Event event) {
		if (event.getEventType() == Event.EventType.COLLISION) {
			Entity entityA = event.getEntityA();
			Entity entityB = event.getEntityB();

			if (entityA.getType() != Entity.Type.BULLET) {
				makeDebris(entityA, event.getWorld());
			}
			if (entityB.getType() != Entity.Type.BULLET) {
				makeDebris(entityB, event.getWorld());
			}
		}
	}

	private void makeDebris(Entity entity, World world) {
		int debrisCount = random.nextInt(3, 7);
		for (int i = 0; i < debrisCount; i++) {
			Entity debris = new Debris(random.nextDouble(0.2));
			debris.setX(entity.getX());
			debris.setY(entity.getY());
			debris.setDX(random.nextDouble(-1, 1));
			debris.setDY(random.nextDouble(-1, 1));
			debris.setRotation(random.nextDouble(360));
			world.addEntity(debris);
		}
	}
}
