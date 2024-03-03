package dk.sdu.mmmi.cbse.common.player;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Player extends Entity {

	public Player() {
		super(Type.PLAYER, new double[]{-10, -10, 20, 0, -10, 10});
		this.setHealth(1);
	}
}
