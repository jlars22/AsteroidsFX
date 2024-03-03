import dk.sdu.mmmi.cbse.common.player.PlayerSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;

module Player {
	exports dk.sdu.mmmi.cbse.playersystem;
	requires Common;
	requires CommonWeapon;
	requires CommonPlayer;

	uses WeaponSPI;

	provides IGamePluginService with dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
	provides IEntityProcessingService with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
	provides IObserver with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
	provides PlayerSPI with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
}
