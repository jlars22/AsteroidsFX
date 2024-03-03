package dk.sdu.mmmi.cbse.scoreservice;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.data.Event.EventType;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreService implements IScoreService, IObserver {

	private final Set<String> scoredEntities = new HashSet<>();
	static AtomicInteger counter = new AtomicInteger(0);

	@Override
	public void addScore(Entity entity) {
		if (entity.getType() == Entity.Type.ENEMY) {
			counter.getAndAdd(200);
		} else if (entity.getType() == Entity.Type.ASTEROID) {
			counter.getAndAdd(160 - 40 * entity.getHealth());
		}
	}

	@Override
	public int getScore() {
		return counter.get();
	}

	@Override
	public void resetScore() {
		counter.set(0);
	}

	@Override
	public void onEvent(Event event) {
			Entity entityA = event.getEntityA();
			Entity entityB = event.getEntityB();

			if (entityA.getType() == Entity.Type.ENEMY || entityA.getType() == Entity.Type.ASTEROID) {
				if (!scoredEntities.contains(entityA.getID())) {
					addScore(entityA);
					scoredEntities.add(entityA.getID());
				}
			}
			if (entityB.getType() == Entity.Type.ENEMY || entityB.getType() == Entity.Type.ASTEROID) {
				if (!scoredEntities.contains(entityB.getID())) {
					addScore(entityB);
					scoredEntities.add(entityB.getID());
				}
			}
	}

	@Override
	public EventType getTopic() {
		return EventType.SCORE_INCREMENT;
	}
}
