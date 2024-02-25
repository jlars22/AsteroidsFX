package dk.sdu.mmmi.cbse.scoreservice;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreService implements IScoreService {

	static AtomicInteger counter = new AtomicInteger(0);

	@Override
	public void addScore(Entity entity) {
		switch (entity.getEntityType()) {
			case ENEMY :
				counter.getAndAdd(200);
				break;
			case ASTEROID :
				Asteroid asteroid = (Asteroid) entity;
				counter.getAndAdd(160 - 40 * asteroid.getSize());
				break;
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
}
