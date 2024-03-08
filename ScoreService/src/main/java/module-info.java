import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.scoreservice.ScoreServiceImpl;

module ScoreService {
	requires CommonScoreService;
	requires Common;

	provides IScoreService with ScoreServiceImpl;
	provides IObserver with ScoreServiceImpl;
}