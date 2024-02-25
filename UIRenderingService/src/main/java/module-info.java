import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
import dk.sdu.mmmi.cbse.uirenderingservice.PlayerHealthRenderingService;

module UIRenderingService {
	requires CommonUIRenderingService;
	requires javafx.graphics;
	requires Common;

	provides IUIRenderingService with PlayerHealthRenderingService;
}