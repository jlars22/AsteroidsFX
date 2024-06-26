import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.debrissystem.DebrisControlSystem;

module Debris {
	requires Common;

	provides IEntityProcessingService with DebrisControlSystem;
	provides IObserver with DebrisControlSystem;
}