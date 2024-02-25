import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.scoreservice.ScoreService;

module ScoreService {
	requires CommonScoreService;
	requires Common;
	requires CommonAsteroid;

	provides IScoreService with ScoreService;
}