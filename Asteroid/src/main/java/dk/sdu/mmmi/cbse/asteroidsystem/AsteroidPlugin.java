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
			asteroid.setX(Math.random() * gameData.getDisplayWidth());
			asteroid.setY(Math.random() * gameData.getDisplayHeight());
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
