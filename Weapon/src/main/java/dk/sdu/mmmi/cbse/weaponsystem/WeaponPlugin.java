package dk.sdu.mmmi.cbse.weaponsystem;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

public class WeaponPlugin implements IGamePluginService, WeaponSPI {
	private final Random random = new Random();

	@Override
	public void start(GameData gameData, World world) {

	}

	@Override
	public void stop(GameData gameData, World world) {

	}

	@Override
	public void shoot(Entity shooter, World world) {
		switch (shooter.getType()) {
			case PLAYER :
				getBulletSPIs()
						.forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, shooter.getRotation())));
				break;
			case ENEMY :
				getBulletSPIs()
						.forEach(bulletSPI -> world.addEntity(bulletSPI.createBullet(shooter, random.nextDouble(360))));
				break;
		}
	}

	private Collection<? extends BulletSPI> getBulletSPIs() {
		return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}
}
