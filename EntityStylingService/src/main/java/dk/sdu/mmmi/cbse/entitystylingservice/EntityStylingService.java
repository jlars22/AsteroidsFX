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
				polygon.setFill(Color.YELLOW);
				break;
			case "Enemy" :
				polygon.setStroke(Color.WHITE);
				polygon.setScaleX(1.5);
				polygon.setScaleY(1.5);
				polygon.setScaleZ(1.5);
				break;
			case "Asteroid" :
			case "Debris" :
				polygon.setStroke(Color.WHITE);
				break;
			case "Player" :
				polygon.setStroke(Color.WHITE);
				polygon.setStrokeWidth(2);
				break;

		}
	}

	private void setEntityWidthAndHeightByPolygon(Entity entity, Polygon polygon) {
		entity.setWidth(polygon.getBoundsInLocal().getWidth());
		entity.setHeight(polygon.getBoundsInLocal().getHeight());
	}
}
