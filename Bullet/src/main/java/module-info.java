import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;

module Bullet {
	requires CommonBullet;

	provides IGamePluginService with BulletPlugin;
	provides BulletSPI with BulletPlugin;
	provides IEntityProcessingService with BulletControlSystem;
	provides IObserver with BulletControlSystem;
}
