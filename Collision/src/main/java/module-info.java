import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
	requires Common;
	requires CommonAsteroid;

	uses dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;

	provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
}
