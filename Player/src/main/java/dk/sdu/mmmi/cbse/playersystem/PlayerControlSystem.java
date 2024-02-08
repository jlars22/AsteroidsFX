package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            handleInput(gameData, world, player);
            updatePosition(player);
            handleBorders(gameData, player);
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
            acceleration = 0.015;
        }
        if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
            fireBullet(gameData, world, player);
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

    private void fireBullet(GameData gameData, World world, Entity player) {
        for (BulletSPI bulletSPI : getBulletSPIs()) {
            Entity bullet = bulletSPI.createBullet(player, gameData);
            world.addEntity(bullet);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}