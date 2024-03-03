import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.player.PlayerSPI;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.mapsystem.MapPlugin;

module Map {
	requires Common;
	requires CommonAsteroid;
	requires CommonPlayer;
	requires CommonEnemy;

	uses AsteroidSPI;
	uses PlayerSPI;
	uses dk.sdu.mmmi.cbse.common.enemy.EnemySPI;

	provides IGamePluginService with MapPlugin;
	provides IObserver with MapPlugin;
}