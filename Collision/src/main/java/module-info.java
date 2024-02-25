import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
	requires Common;
	requires CommonAsteroid;
	requires CommonScoreService;
	requires CommonBullet;
	requires Enemy;
	requires Player;

	uses dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
	uses dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;

	provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
}
