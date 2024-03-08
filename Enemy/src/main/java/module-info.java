import dk.sdu.mmmi.cbse.common.enemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;

module Enemy {
	exports dk.sdu.mmmi.cbse.enemysystem;
	requires CommonWeapon;
	requires CommonEnemy;

	uses WeaponSPI;

	provides IGamePluginService with EnemyPlugin;
	provides IEntityProcessingService with EnemyControlSystem;
	provides IObserver with EnemyControlSystem;
	provides EnemySPI with EnemyControlSystem;
}
