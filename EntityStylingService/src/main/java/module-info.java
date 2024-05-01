import dk.sdu.mmmi.cbse.common.services.IEntityStylingService;
import dk.sdu.mmmi.cbse.entitystylingservice.EntityStylingServiceImpl;

module EntityStylingService {
	requires Common;
	requires javafx.graphics;

	provides IEntityStylingService with EntityStylingServiceImpl;
}