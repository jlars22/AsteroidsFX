package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.time.LocalTime;

public class CollisionControlSystem implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entityA : world.getEntities()) {
            for (Entity entityB : world.getEntities()) {
                if (entityA.getID().equals(entityB.getID())) {
                    continue;
                }
                if (checkCollision(entityA, entityB)) {
                    handleCollision(entityA, entityB, world);
                }
            }
        }
    }

    private void handleCollision(Entity entityA, Entity entityB, World world) {
        // TODO: Implement collision handling
        System.out.println(LocalTime.now()+ "Collision between " + entityA.getEntityType() + " and " + entityB.getEntityType());
    }

    private void decrementHealthOrRemoveEntity(Entity entity, World world) {
        if (entity.getHealth() > 1) {
            entity.setHealth(entity.getHealth() - 1);
        } else {
            world.removeEntity(entity);
        }
    }

    private boolean checkCollision(Entity entityA, Entity entityB) {
        if (areBothEntitiesBullets(entityA, entityB)) return false;
        if (isBulletFromOwner(entityA, entityB)) return false;

        double aLeft = entityA.getX();
        double aRight = entityA.getX() + entityA.getWidth();
        double aTop = entityA.getY();
        double aBottom = entityA.getY() + entityA.getHeight();

        double bLeft = entityB.getX();
        double bRight = entityB.getX() + entityB.getWidth();
        double bTop = entityB.getY();
        double bBottom = entityB.getY() + entityB.getHeight();

        // Check if the bounding boxes overlap
        return aLeft < bRight && aRight > bLeft && aTop < bBottom && aBottom > bTop;
    }

    private boolean areBothEntitiesBullets(Entity entityA, Entity entityB) {
        return entityA.getEntityType().equals(Entity.EntityType.BULLET)
                && entityB.getEntityType().equals(Entity.EntityType.BULLET);
    }

    private boolean isBulletFromOwner(Entity entityA, Entity entityB) {
        Entity bullet = entityA.getEntityType().equals(Entity.EntityType.BULLET) ? entityA : entityB;
        Entity otherEntity = entityA.getEntityType().equals(Entity.EntityType.BULLET) ? entityB : entityA;

        return bullet.getEntityType().equals(Entity.EntityType.BULLET)
                && ((Bullet) bullet).getOwner().getID().equals(otherEntity.getID());
    }
}
