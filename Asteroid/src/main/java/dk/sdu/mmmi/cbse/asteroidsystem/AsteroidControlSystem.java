package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService, AsteroidSPI {
    @Override
    public void process(GameData gameData, World world) {
        // Make the asteroids float around in their current direction
        for (Entity entity : world.getEntities(Asteroid.class)) {
            Asteroid asteroid = (Asteroid) entity;
            handleTravel(asteroid);
            handleBorders(gameData, asteroid);
        }
    }

    private void handleTravel(Asteroid asteroid) {
        asteroid.setX(asteroid.getX() + asteroid.getDX());
        asteroid.setY(asteroid.getY() + asteroid.getDY());
    }

    private void handleBorders(GameData gameData, Asteroid asteroid) {
        double asteroidWidth = asteroid.getWidth();
        double asteroidHeight = asteroid.getHeight();

        if (asteroid.getX() < -asteroidWidth) {
            asteroid.setX(gameData.getDisplayWidth());
        } else if (asteroid.getX() > gameData.getDisplayWidth()) {
            asteroid.setX(-asteroidWidth);
        }

        if (asteroid.getY() < -asteroidHeight) {
            asteroid.setY(gameData.getDisplayHeight());
        } else if (asteroid.getY() > gameData.getDisplayHeight()) {
            asteroid.setY(-asteroidHeight);
        }
    }

    @Override
    public void splitAsteroid(Entity entity, World world) {
        Asteroid asteroid = (Asteroid) entity;
        world.removeEntity(asteroid);
        for (int i = 0; i < 2; i++) {
            Asteroid newAsteroid = new Asteroid(asteroid.getSize() - 1);
            newAsteroid.setX(asteroid.getX());
            newAsteroid.setY(asteroid.getY());
            newAsteroid.setDX(Math.random() * 2 - 1);
            newAsteroid.setDY(Math.random() * 2 - 1);
            newAsteroid.setRotation(Math.random() * 360);
            world.addEntity(newAsteroid);
        }
    }
}

