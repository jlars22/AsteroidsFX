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
            double acceleration = 0;
            double deceleration = 0.995;

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
                for (BulletSPI bulletSPI : getBulletSPIs()) {
                    Entity bullet = bulletSPI.createBullet(player, gameData);
                    world.addEntity(bullet);
                }
            }

            double changeX = Math.cos(Math.toRadians(player.getRotation())) * acceleration;
            double changeY = Math.sin(Math.toRadians(player.getRotation())) * acceleration;

            player.setX(player.getX() + player.getDX() + changeX);
            player.setY(player.getY() + player.getDY() + changeY);

            player.setDX((player.getDX() + changeX) * deceleration);
            player.setDY((player.getDY() + changeY) * deceleration);



            if (player.getX() < 0) {
                player.setX(gameData.getDisplayWidth());
            }

            if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(0);
            }

            if (player.getY() < 0) {
                player.setY(gameData.getDisplayHeight());
            }

            if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(0);
            }
        }
    }


    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
