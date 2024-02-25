import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
import dk.sdu.mmmi.cbse.uirenderingservice.PlayerHealthRenderingService;
import dk.sdu.mmmi.cbse.uirenderingservice.ScoreRenderingService;

module UIRenderingService {
	uses dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
	requires CommonUIRenderingService;
	requires javafx.graphics;
	requires Common;
	requires CommonScoreService;

	provides IUIRenderingService with PlayerHealthRenderingService, ScoreRenderingService;
}