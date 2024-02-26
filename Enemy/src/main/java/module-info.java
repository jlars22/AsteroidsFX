import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;

module Enemy {
	exports dk.sdu.mmmi.cbse.enemysystem;
	requires Common;
	requires CommonBullet;

	uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;

	provides IGamePluginService with dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
	provides IEntityProcessingService with dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
	provides IObserver with dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
}
