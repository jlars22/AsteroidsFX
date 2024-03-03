package dk.sdu.mmmi.cbse.mapsystem;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.player.PlayerSPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.data.Event.EventType;
import java.util.Collection;
import java.util.ServiceLoader;

public class MapPlugin implements IGamePluginService, IObserver {
	private int level = 1;
	private int asteroidsCount = 3;

	@Override
	public void start(GameData gameData, World world) {
		createAsteroids(world, gameData);
	}

	@Override
	public void stop(GameData gameData, World world) {

	}

	@Override
	public void onEvent(Event event) {
			level++;
			asteroidsCount += 3;
			resetPlayer(event.getGameData(), event.getWorld());
			resetEnemy(event.getGameData(), event.getWorld());
			createAsteroids(event.getWorld(), event.getGameData());
	}

	@Override
	public EventType getTopic() {
		return EventType.NEW_LEVEL;
	}

	private void createAsteroids(World world, GameData gameData) {
		for (AsteroidSPI asteroidSPI : getAsteroidSPIs()) {
			asteroidSPI.createAsteroid(asteroidsCount, world, gameData);
		}
	}

	private void resetPlayer(GameData gameData, World world) {
		for (PlayerSPI playerSPI : getPlayerSPIs()) {
			playerSPI.resetPlayerPosition(gameData, world);
		}
	}

	private void resetEnemy(GameData gameData, World world) {
		for (EnemySPI enemySPI : getEnemySPIs()) {
			enemySPI.resetEnemyPosition(gameData, world);
		}
	}

	private Collection<? extends AsteroidSPI> getAsteroidSPIs() {
		return ServiceLoader.load(AsteroidSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

	private Collection<? extends PlayerSPI> getPlayerSPIs() {
		return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

	private Collection<? extends EnemySPI> getEnemySPIs() {
		return ServiceLoader.load(EnemySPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

}
