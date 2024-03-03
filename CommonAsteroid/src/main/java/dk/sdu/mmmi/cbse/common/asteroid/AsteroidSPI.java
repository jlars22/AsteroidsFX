package dk.sdu.mmmi.cbse.common.asteroid;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSPI {

	void createAsteroid(int count, World world, GameData gameData);
}
