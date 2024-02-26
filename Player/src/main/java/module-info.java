import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;

module Player {
	exports dk.sdu.mmmi.cbse.playersystem;
	requires Common;
	requires CommonBullet;

	uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;

	provides IGamePluginService with dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
	provides IEntityProcessingService with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
	provides IObserver with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
}
