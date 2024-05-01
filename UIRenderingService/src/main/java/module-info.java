import dk.sdu.mmmi.cbse.common.services.IUIRenderingService;
import dk.sdu.mmmi.cbse.uirenderingservice.PlayerHealthRenderingService;
import dk.sdu.mmmi.cbse.uirenderingservice.ScoreRenderingService;

module UIRenderingService {
	uses dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
	requires CommonScoreService;
	requires javafx.graphics;

	provides IUIRenderingService with PlayerHealthRenderingService, ScoreRenderingService;
}