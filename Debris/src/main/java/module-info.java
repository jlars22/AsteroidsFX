import dk.sdu.mmmi.cbse.common.debris.DebrisSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

module Debris {
	requires Common;
	requires CommonDebris;

	provides DebrisSPI with dk.sdu.mmmi.cbse.debrissystem.DebrisControlSystem;
	provides IEntityProcessingService with dk.sdu.mmmi.cbse.debrissystem.DebrisControlSystem;
}