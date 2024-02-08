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
            world.addEntity(createEnemyShip(gameData));
        }

        for (Entity entity : world.getEntities(Enemy.class)) {
            Enemy enemy = (Enemy) entity;
            startFiring(gameData, world, enemy);
            startMovement(enemy);
            handleBorders(gameData, enemy);
        }
    }

    private boolean shouldCreateNewEnemy(World world) {
        // TODO: Make these spawns randomly and not just after all enemies are dead
        return world.getEntities(Enemy.class).isEmpty();
    }

    private void handleBorders(GameData gameData, Enemy enemy) {
        if (enemy.getX() < 0) {
            enemy.setX(gameData.getDisplayWidth());
        } else if (enemy.getX() > gameData.getDisplayWidth()) {
            enemy.setX(0);
        }

        if (enemy.getY() < 0) {
            enemy.setY(gameData.getDisplayHeight());
        } else if (enemy.getY() > gameData.getDisplayHeight()) {
            enemy.setY(0);
        }
    }

    private void startMovement(Enemy enemy) {
        double speed = 0.7;

        // If the enemy is not moving, set a random direction
        if (enemy.getDX() == 0 && enemy.getDY() == 0) {
            double direction = random.nextDouble(2 * Math.PI);
            enemy.setDX(Math.cos(direction) * speed);
            enemy.setDY(Math.sin(direction) * speed);
        }

        if (shouldChangeDirection(enemy)) {
            double currentDirection = Math.atan2(enemy.getDY(), enemy.getDX());
            double adjustment = Math.toRadians(random.nextInt(-45, 45));
            double newDirection = currentDirection + adjustment;

            enemy.setDX(Math.cos(newDirection) * speed);
            enemy.setDY(Math.sin(newDirection) * speed);

        }


        enemy.setX(enemy.getX() + enemy.getDX());
        enemy.setY(enemy.getY() + enemy.getDY());
    }

    private boolean shouldChangeDirection(Enemy enemy) {
        LocalTime currentTime = LocalTime.now();
        LocalTime lastTimeChangedDirection = enemy.getLastTimeChangedDirection();

        if (lastTimeChangedDirection != null && !currentTime.isAfter(lastTimeChangedDirection.plusSeconds(3))) {
            return false;
        }

        enemy.setLastTimeChangedDirection(currentTime);
        return true;
    }


    private void startFiring(GameData gameData, World world, Enemy enemy) {
        LocalTime currentTime = LocalTime.now();
        LocalTime lastTimeFired = enemy.getLastTimeFired();

        if (lastTimeFired == null || currentTime.isAfter(lastTimeFired.plusSeconds(3))) {
            getBulletSPIs().stream().findFirst().ifPresent(spi -> world.addEntity(spi.createBulletRandomDirection(enemy, gameData)));
            enemy.setLastTimeFired(currentTime);
        }
    }

    private Enemy createEnemyShip(GameData gameData) {
        Enemy enemyShip = new Enemy();
        setRandomPosition(gameData, enemyShip);
        return enemyShip;
    }

    private void setRandomPosition(GameData gameData, Enemy eneemy) {
        switch (random.nextInt(4)) {
            case 0:
                eneemy.setX(0);
                eneemy.setY(random.nextInt(gameData.getDisplayHeight()));
                break;
            case 1:
                eneemy.setX(gameData.getDisplayWidth());
                eneemy.setY(random.nextInt(gameData.getDisplayHeight()));
                break;
            case 2:
                eneemy.setX(random.nextInt(gameData.getDisplayWidth()));
                eneemy.setY(0);
                break;
            case 3:
                eneemy.setX(random.nextInt(gameData.getDisplayWidth()));
                eneemy.setY(gameData.getDisplayHeight());
                break;
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}