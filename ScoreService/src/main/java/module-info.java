import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.services.IObserver;

module ScoreService {
    requires Common;
    requires CommonScoreService;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.context;

    exports dk.sdu.mmmi.cbse.scoreservice;
}