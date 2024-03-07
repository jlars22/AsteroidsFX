import dk.sdu.mmmi.cbse.common.entitystylingservice.IEntityStylingService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;

module Core {
	requires Common;
	requires javafx.graphics;
	requires CommonUIRenderingService;
	requires CommonEntityStylingService;

	opens dk.sdu.mmmi.cbse.main to javafx.graphics;

	uses IGamePluginService;
	uses IEntityProcessingService;
	uses IPostEntityProcessingService;
	uses IUIRenderingService;
	uses IEntityStylingService;
	uses IObserver;
}
