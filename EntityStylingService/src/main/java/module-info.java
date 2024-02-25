import dk.sdu.mmmi.cbse.common.entitystylingservice.IEntityStylingService;
import dk.sdu.mmmi.cbse.entitystylingservice.EntityStylingService;

module EntityStylingService {
	requires CommonEntityStylingService;
	requires javafx.graphics;
	requires Common;

	provides IEntityStylingService with EntityStylingService;
}