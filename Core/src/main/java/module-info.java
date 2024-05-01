import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IEntityStylingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IUIRenderingService;

module Core {
	requires Common;
	requires javafx.graphics;
	requires spring.context;
	requires java.annotation;
	exports dk.sdu.mmmi.cbse.main;

	opens dk.sdu.mmmi.cbse.main to javafx.graphics, spring.core;

	uses IGamePluginService;
	uses IEntityProcessingService;
	uses IPostEntityProcessingService;
	uses IUIRenderingService;
	uses IEntityStylingService;
	uses IObserver;
}
