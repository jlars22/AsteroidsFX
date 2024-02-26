module Core {
	requires Common;
	requires CommonBullet;
	requires javafx.graphics;
	requires CommonUIRenderingService;
	requires CommonEntityStylingService;

	opens dk.sdu.mmmi.cbse.main to javafx.graphics;

	uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
	uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
	uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
	uses dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
	uses dk.sdu.mmmi.cbse.common.entitystylingservice.IEntityStylingService;
	uses dk.sdu.mmmi.cbse.common.services.IObserver;
}
