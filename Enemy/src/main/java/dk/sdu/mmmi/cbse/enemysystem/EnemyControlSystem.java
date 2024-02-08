package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    private final Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        if (shouldCreateNewEnemy(world)) {
            Entity enemyShip = createEnemyShip(gameData);
            world.addEntity(enemyShip);
        }

        for (Entity enemy : world.getEntities(Enemy.class)) {
            startFiring(gameData, world, enemy);
            startMovement(gameData, enemy);
            handleBorders(gameData, enemy);
        }
    }

    private boolean shouldCreateNewEnemy(World world) {
        return world.getEntities(Enemy.class).isEmpty();
    }

    private void handleBorders(GameData gameData, Entity entity) {
        if (entity.getX() < 0) {
            entity.setX(gameData.getDisplayWidth());
        } else if (entity.getX() > gameData.getDisplayWidth()) {
            entity.setX(0);
        }

        if (entity.getY() < 0) {
            entity.setY(gameData.getDisplayHeight());
        } else if (entity.getY() > gameData.getDisplayHeight()) {
            entity.setY(0);
        }
    }

    private void startMovement(GameData gameData, Entity enemy) {
        double speed = 0.8;

        if (enemy.getDX() == 0 && enemy.getDY() == 0) {
            double direction = random.nextDouble() * 2 * Math.PI;
            enemy.setDX(Math.cos(direction) * speed);
            enemy.setDY(Math.sin(direction) * speed);
        }

        if (random.nextInt(100) < 1) { // Decide if the enemy should change direction
            double direction = random.nextDouble() * 2 * Math.PI;
            enemy.setDX(Math.cos(direction) * speed);
            enemy.setDY(Math.sin(direction) * speed);
        }

        enemy.setX(enemy.getX() + enemy.getDX());
        enemy.setY(enemy.getY() + enemy.getDY());
    }

    private void startFiring(GameData gameData, World world, Entity enemy) {
        LocalTime currentTime = LocalTime.now();
        LocalTime lastTimeFired = ((Enemy) enemy).getLastTimeFired();

        if (lastTimeFired == null || currentTime.isAfter(lastTimeFired.plusSeconds(3))) {
            for (BulletSPI bulletSPI : getBulletSPIs()) {
                Entity bullet = bulletSPI.createBulletRandomDirection(enemy, gameData);
                world.addEntity(bullet);
            }
            ((Enemy) enemy).setLastTimeFired(currentTime);
        }
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        setStyle(enemyShip);
        setRandomPosition(gameData, enemyShip);
        return enemyShip;
    }

    private void setRandomPosition(GameData gameData, Entity entity) {
        switch (random.nextInt(4)) {
            case 0:
                entity.setX(0);
                entity.setY(random.nextInt(gameData.getDisplayHeight()));
                break;
            case 1:
                entity.setX(gameData.getDisplayWidth());
                entity.setY(random.nextInt(gameData.getDisplayHeight()));
                break;
            case 2:
                entity.setX(random.nextInt(gameData.getDisplayWidth()));
                entity.setY(0);
                break;
            case 3:
                entity.setX(random.nextInt(gameData.getDisplayWidth()));
                entity.setY(gameData.getDisplayHeight());
                break;
        }
    }

    private void setStyle(Entity entity) {
        entity.setPolygonCoordinates(0, -10, -7, -3, -7, 3, 0, 10, 7, 3, 7, -3);
        entity.setColor("RED");
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}