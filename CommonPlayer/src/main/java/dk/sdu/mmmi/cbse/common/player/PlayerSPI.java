package dk.sdu.mmmi.cbse.common.player;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface PlayerSPI {

	void resetPlayerPosition(GameData gameData, World world);
}
