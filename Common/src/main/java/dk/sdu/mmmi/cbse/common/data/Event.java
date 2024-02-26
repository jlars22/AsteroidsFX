package dk.sdu.mmmi.cbse.common.data;

public class Event {

	public enum EventType {
		COLLISION, SCORE_INCREMENT, GAME_OVER
	}

	private final Entity entityA;
	private final Entity entityB;
	private final EventType eventType;
	private final World world;

	public Event(Entity entityA, Entity entityB, EventType eventType, World world) {
		this.entityA = entityA;
		this.entityB = entityB;
		this.eventType = eventType;
		this.world = world;
	}

	public Entity getEntityA() {
		return entityA;
	}

	public Entity getEntityB() {
		return entityB;
	}

	public EventType getEventType() {
		return eventType;
	}

	public World getWorld() {
		return world;
	}
}
