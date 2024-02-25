import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
	// This could use some refactoring to reduce the number of required modules
	requires Common;
	requires CommonAsteroid;
	requires CommonScoreService;
	requires CommonBullet;
	requires Enemy;
	requires Player;
	requires CommonDebris;

	uses dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
	uses dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
	uses dk.sdu.mmmi.cbse.common.debris.DebrisSPI;

	provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
}
