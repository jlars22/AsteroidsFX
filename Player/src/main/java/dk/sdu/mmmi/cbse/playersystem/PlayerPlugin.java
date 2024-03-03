package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

	@Override
	public void start(GameData gameData, World world) {
		Entity player = createPlayerShip(gameData);
		world.addEntity(player);
		gameData.setPlayer(player);
	}

	private Entity createPlayerShip(GameData gameData) {
		Entity playerShip = new Player();
		playerShip.setX(gameData.getDisplayHeight() / 2);
		playerShip.setY(gameData.getDisplayWidth() / 2);
		return playerShip;
	}

	@Override
	public void stop(GameData gameData, World world) {
		for (Entity e : world.getEntities()) {
			if (e.getClass() == Player.class) {
				world.removeEntity(e);
			}
		}
	}
}
