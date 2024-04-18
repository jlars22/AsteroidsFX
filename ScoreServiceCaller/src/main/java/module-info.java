import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.services.IObserver;

module ScoreServiceCaller {
	requires Common;
	requires CommonScoreService;
	requires java.net.http;

	provides IObserver with dk.sdu.mmmi.cbse.scoreservicecaller.ScoreServiceImpl;
	provides IScoreService with dk.sdu.mmmi.cbse.scoreservicecaller.ScoreServiceImpl;

}