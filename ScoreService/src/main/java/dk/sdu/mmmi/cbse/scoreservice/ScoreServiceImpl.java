package dk.sdu.mmmi.cbse.scoreservice;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.Event.EventType;
import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreServiceImpl implements IScoreService, IObserver {

	private final Set<String> scoredEntities = new HashSet<>();
	static AtomicInteger score = new AtomicInteger(0);
	static AtomicInteger level = new AtomicInteger(1);

	@Override
	public void addScore(Entity entity) {
		if (entity.getType() == Entity.Type.ENEMY) {
			score.getAndAdd(200);
		} else if (entity.getType() == Entity.Type.ASTEROID) {
			score.getAndAdd(160 - 40 * entity.getHealth());
		}
	}

	@Override
	public int getScore() {
		return score.get();
	}

	public void resetScore() {
		score.set(0);
	}

	@Override
	public int getLevel() {
		return level.get();
	}

	public void resetLevel() {
		level.set(1);
	}

	@Override
	public void onEvent(Event event) {
		if (event.getEventType() == EventType.NEW_LEVEL) {
			level.getAndIncrement();
		}

		if (event.getEventType() == EventType.SCORE_INCREMENT) {
			incrementScore(event);
		}
	}

	private void incrementScore(Event event) {
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
	public List<EventType> getTopics() {
		return List.of(EventType.SCORE_INCREMENT, EventType.NEW_LEVEL);
	}
}
