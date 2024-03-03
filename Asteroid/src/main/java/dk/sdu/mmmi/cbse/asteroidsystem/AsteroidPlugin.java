package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

public class AsteroidPlugin implements IGamePluginService, AsteroidSPI {

	private final Random random = new Random();
	@Override
	public void start(GameData gameData, World world) {

	}

	@Override
	public void stop(GameData gameData, World world) {
		for (Entity e : world.getEntities()) {
			if (e.getClass() == Asteroid.class) {
				world.removeEntity(e);
			}
		}
	}

	@Override
	public void createAsteroid(int count, World world, GameData gameData) {
		for (int i = 0; i < count; i++) {
			Entity asteroid = new Asteroid(3);

			// Define a buffer zone around the edges for the player not to die instantly
			double bufferZone = 0.1;
			double minX = gameData.getDisplayWidth() * bufferZone;
			double maxX = gameData.getDisplayWidth() * (1 - bufferZone);
			double minY = gameData.getDisplayHeight() * bufferZone;
			double maxY = gameData.getDisplayHeight() * (1 - bufferZone);

			double x = random.nextDouble() < 0.5
					? random.nextDouble() * minX
					: random.nextDouble() * (maxX - gameData.getDisplayWidth()) + gameData.getDisplayWidth();
			double y = random.nextDouble() < 0.5
					? random.nextDouble() * minY
					: random.nextDouble() * (maxY - gameData.getDisplayHeight()) + gameData.getDisplayHeight();

			asteroid.setX(x);
			asteroid.setY(y);
			asteroid.setDY(random.nextDouble(-1, 1));
			asteroid.setDX(random.nextDouble(-1, 1));
			asteroid.setRotation(random.nextDouble(360));
			world.addEntity(asteroid);
		}
	}
}
