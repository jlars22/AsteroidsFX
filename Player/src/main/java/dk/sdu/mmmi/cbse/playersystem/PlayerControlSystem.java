package dk.sdu.mmmi.cbse.playersystem;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import dk.sdu.mmmi.cbse.common.player.PlayerSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.weapon.WeaponSPI;
import java.time.LocalTime;
import java.util.Collection;
import java.util.ServiceLoader;

public class PlayerControlSystem implements IEntityProcessingService, IObserver, PlayerSPI {

	@Override
	public void process(GameData gameData, World world) {
		respawnPlayer(gameData, world);
		for (Entity player : world.getEntities(Player.class)) {
			handleInput(gameData, world, player);
			updatePosition(player);
			handleBorders(gameData, player);
		}
	}

	@Override
	public void onEvent(Event event) {
		if (event.getEventType() == Event.EventType.COLLISION) {
			Entity entityA = event.getEntityA();
			Entity entityB = event.getEntityB();

			if (entityA instanceof Player) {
				event.getWorld().removeEntity(entityA);
				entityA.setHealth(entityA.getHealth() - 1);
			}
			if (entityB instanceof Player) {
				event.getWorld().removeEntity(entityB);
				entityB.setHealth(entityB.getHealth() - 1);
			}
		}
	}

	@Override
	public void resetPlayerPosition(GameData gameData, World world) {
		for (Entity player : world.getEntities(Player.class)) {
			world.removeEntity(gameData.getPlayer());
			player.setX(gameData.getDisplayWidth() / 2);
			player.setY(gameData.getDisplayHeight() / 2);
			player.setDX(0);
			player.setDY(0);
			world.addEntity(gameData.getPlayer());
		}
	}

	private void respawnPlayer(GameData gameData, World world) {
		if (world.getEntities(Player.class).isEmpty()) {
			Player player = (Player) gameData.getPlayer();
			if (player.getHealth() == 0) {
				System.out.println("Player is dead");
				return;
			}
			if (player.getRespawnTime() == null) {
				player.setRespawnTime(LocalTime.now());
			} else if (LocalTime.now().isAfter(player.getRespawnTime().plusSeconds(3))) {
				player.setX(gameData.getDisplayWidth() / 2);
				player.setY(gameData.getDisplayHeight() / 2);
				player.setDX(0);
				player.setDY(0);
				world.addEntity(gameData.getPlayer());
				player.setRespawnTime(null);
			}
		}
	}

	private void handleInput(GameData gameData, World world, Entity player) {
		double acceleration = 0;

		if (gameData.getKeys().isDown(GameKeys.LEFT)) {
			player.setRotation(player.getRotation() - 2);
		}
		if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
			player.setRotation(player.getRotation() + 2);
		}
		if (gameData.getKeys().isDown(GameKeys.UP)) {
			acceleration = 0.02;
		}
		if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
			fireBullet(world, player);
		}

		applyAcceleration(player, acceleration);
	}

	private void applyAcceleration(Entity player, double acceleration) {
		double deceleration = 0.995;
		double changeX = Math.cos(Math.toRadians(player.getRotation())) * acceleration;
		double changeY = Math.sin(Math.toRadians(player.getRotation())) * acceleration;

		player.setDX((player.getDX() + changeX) * deceleration);
		player.setDY((player.getDY() + changeY) * deceleration);
	}

	private void updatePosition(Entity player) {
		player.setX(player.getX() + player.getDX());
		player.setY(player.getY() + player.getDY());
	}

	private void handleBorders(GameData gameData, Entity player) {
		if (player.getX() < 0) {
			player.setX(gameData.getDisplayWidth());
		} else if (player.getX() > gameData.getDisplayWidth()) {
			player.setX(0);
		}

		if (player.getY() < 0) {
			player.setY(gameData.getDisplayHeight());
		} else if (player.getY() > gameData.getDisplayHeight()) {
			player.setY(0);
		}
	}

	private void fireBullet(World world, Entity player) {
		getWeaponSPIs().stream().findFirst().ifPresent(spi -> {
			spi.shoot(player, world);
		});
	}

	private Collection<? extends WeaponSPI> getWeaponSPIs() {
		return ServiceLoader.load(WeaponSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

}
