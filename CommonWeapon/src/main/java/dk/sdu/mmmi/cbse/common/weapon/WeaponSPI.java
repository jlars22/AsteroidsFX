package dk.sdu.mmmi.cbse.common.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface WeaponSPI {

	void shoot(Entity shooter, World world);
}
