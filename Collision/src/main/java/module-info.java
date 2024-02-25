import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
	requires Common;
	requires CommonAsteroid;
	requires CommonScoreService;
	requires CommonBullet;
	requires CommonDebris;

	uses dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
	uses dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
	uses dk.sdu.mmmi.cbse.common.debris.DebrisSPI;

	provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
}
