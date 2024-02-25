package dk.sdu.mmmi.cbse.entitystylingservice;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.entitystylingservice.IEntityStylingService;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class EntityStylingService implements IEntityStylingService {
	@Override
	public void styleEntity(Entity entity, Polygon polygon) {
		setPolygonStylingByInstance(entity, polygon);
		setEntityWidthAndHeightByPolygon(entity, polygon);
	}

	private void setPolygonStylingByInstance(Entity entity, Polygon polygon) {
		switch (entity.getClass().getSimpleName()) {
			case "Bullet" :
				polygon.setFill(Color.valueOf(entity.getColor()));
				break;
			case "Enemy" :
				polygon.setStroke(Color.valueOf(entity.getColor()));
				polygon.setScaleX(1.5);
				polygon.setScaleY(1.5);
				polygon.setScaleZ(1.5);
				break;
			case "Asteroid" :
				polygon.setStroke(Color.valueOf(entity.getColor()));
				break;
			case "Player" :
				polygon.setStroke(Color.valueOf(entity.getColor()));
				polygon.setStrokeWidth(2);
				break;
		}
	}

	private void setEntityWidthAndHeightByPolygon(Entity entity, Polygon polygon) {
		entity.setWidth(polygon.getBoundsInLocal().getWidth());
		entity.setHeight(polygon.getBoundsInLocal().getHeight());
	}
}
