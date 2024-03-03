import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;
import dk.sdu.mmmi.cbse.weaponsystem.WeaponPlugin;

module Weapon {
	requires Common;
	requires CommonWeapon;
	requires CommonBullet;

	uses BulletSPI;

	provides WeaponSPI with WeaponPlugin;
}