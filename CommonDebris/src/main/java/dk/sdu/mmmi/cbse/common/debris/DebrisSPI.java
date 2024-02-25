package dk.sdu.mmmi.cbse.common.debris;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface DebrisSPI {
	void createDebris(Entity entity, World world);
}
