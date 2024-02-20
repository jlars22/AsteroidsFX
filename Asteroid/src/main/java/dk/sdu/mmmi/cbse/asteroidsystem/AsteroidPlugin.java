package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {
	@Override
	public void start(GameData gameData, World world) {
		for (int i = 0; i < 4; i++) {
			Entity asteroid = new Asteroid(3);

			// Define a buffer zone around the edges for the player not to die instantly
			double bufferZone = 0.1;
			double minX = gameData.getDisplayWidth() * bufferZone;
			double maxX = gameData.getDisplayWidth() * (1 - bufferZone);
			double minY = gameData.getDisplayHeight() * bufferZone;
			double maxY = gameData.getDisplayHeight() * (1 - bufferZone);

			double x = Math.random() < 0.5
					? Math.random() * minX
					: Math.random() * (maxX - gameData.getDisplayWidth()) + gameData.getDisplayWidth();
			double y = Math.random() < 0.5
					? Math.random() * minY
					: Math.random() * (maxY - gameData.getDisplayHeight()) + gameData.getDisplayHeight();

			asteroid.setX(x);
			asteroid.setY(y);
			asteroid.setDY(Math.random() * 2 - 1);
			asteroid.setDX(Math.random() * 2 - 1);
			asteroid.setRotation(Math.random() * 360);
			world.addEntity(asteroid);
		}
	}

	@Override
	public void stop(GameData gameData, World world) {
	}
}
