package dk.sdu.mmmi.cbse.common.entitystylingservice;

import dk.sdu.mmmi.cbse.common.data.Entity;
import javafx.scene.shape.Polygon;

public interface IEntityStylingService {

	void styleEntity(Entity entity, Polygon polygon);
}
