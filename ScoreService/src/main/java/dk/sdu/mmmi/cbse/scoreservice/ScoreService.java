package dk.sdu.mmmi.cbse.scoreservice;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.enemysystem.Enemy;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreService implements IScoreService {

	static AtomicInteger counter = new AtomicInteger(0);

	@Override
	public void addScore(Entity entity) {
		if (entity instanceof Enemy) {
			counter.getAndAdd(200);
		} else if (entity instanceof Asteroid) {
			Asteroid asteroid = (Asteroid) entity;
			counter.getAndAdd(160 - 40 * asteroid.getSize());
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
