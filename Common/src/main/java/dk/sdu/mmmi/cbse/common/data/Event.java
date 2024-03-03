package dk.sdu.mmmi.cbse.common.data;

public class Event {

	public enum EventType {
		COLLISION, SCORE_INCREMENT, NEW_LEVEL, GAME_OVER
	}

	private final Entity entityA;
	private final Entity entityB;
	private final EventType eventType;
	private final World world;
	private final GameData gameData;

	public Event(Entity entityA, Entity entityB, EventType eventType, World world, GameData gameData) {
		this.entityA = entityA;
		this.entityB = entityB;
		this.eventType = eventType;
		this.world = world;
		this.gameData = gameData;
	}

	public Event(EventType eventType, World world, GameData gameData) {
		this.entityA = null;
		this.entityB = null;
		this.eventType = eventType;
		this.world = world;
		this.gameData = gameData;
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

	public GameData getGameData() {
		return gameData;
	}
}
