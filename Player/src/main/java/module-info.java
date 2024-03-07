import dk.sdu.mmmi.cbse.common.player.PlayerSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;

module Player {
	exports dk.sdu.mmmi.cbse.playersystem;
	requires Common;
	requires CommonWeapon;
	requires CommonPlayer;

	uses WeaponSPI;

	provides IGamePluginService with PlayerPlugin;
	provides IEntityProcessingService with PlayerControlSystem;
	provides IObserver with PlayerControlSystem;
	provides PlayerSPI with PlayerControlSystem;
}
