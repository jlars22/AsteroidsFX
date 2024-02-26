import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.scoreservice.ScoreService;

module ScoreService {
	requires CommonScoreService;
	requires Common;

	provides IScoreService with ScoreService;
	provides IObserver with ScoreService;
}