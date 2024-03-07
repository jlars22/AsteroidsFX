import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidControlSystem;
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidPlugin;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;

module Asteroid {
	requires Common;
	requires CommonAsteroid;

	provides IGamePluginService with AsteroidPlugin;
	provides IEntityProcessingService with AsteroidControlSystem;
	provides IObserver with AsteroidControlSystem;
	provides AsteroidSPI with AsteroidPlugin;
}
