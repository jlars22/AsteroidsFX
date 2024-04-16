package dk.sdu.mmmi.cbse.playersystem;

import static org.junit.jupiter.api.Assertions.*;

import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.Event.EventType;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class PlayerControlSystemTests {

	private PlayerControlSystem playerControlSystem;
	private GameData gameData;
	private World world;
	private Player player;

	@BeforeEach
	public void setUp() {
		playerControlSystem = new PlayerControlSystem();
		gameData = new GameData();
		world = new World();
		player = new Player();
		world.addEntity(player);
		gameData.setPlayer(player);
	}

	@Test
	public void Given_PlayerIsInStartPosition_When_UpKeyIsPressed_Then_PlayerPositionChanges() {
		// Given
		player.setX(0);
		player.setY(0);
		player.setRotation(90);

		// When
		gameData.getKeys().setKey(GameKeys.UP, true);
		playerControlSystem.process(gameData, world);

		// Then
		assertNotEquals(0, player.getX());
		assertNotEquals(0, player.getY());
	}

	@ParameterizedTest
	@MethodSource("provideLeftRightGameKeys")
	public void Given_PlayerIsRotating_When_LeftOrRightKeyIsPressed_Then_PlayerRotationChanges(int key) {
		// Given
		player.setRotation(0);

		// When
		gameData.getKeys().setKey(key, true);
		playerControlSystem.process(gameData, world);

		// Then
		assertNotEquals(0, player.getRotation());
	}

	@Test
	public void Given_CollisionEventOccurs_When_onEventIsCalled_Then_PlayerHealthDecreasesAndPlayerIsRemovedFromWorld() {
		// Given
		Player playerA = new Player();
		Player playerB = new Player();
		playerA.setHealth(1);
		world.addEntity(playerA);
		world.addEntity(playerB);
		Event event = new Event(playerA, playerB, EventType.COLLISION, world, gameData);

		// When
		playerControlSystem.onEvent(event);

		// Then
		assertFalse(world.getEntities().contains(playerA));
		assertEquals(playerA.getHealth(), 0);
		assertEquals(playerB.getHealth(), 2);
	}

	@Test
	public void Given_PlayerControlSystem_When_getTopicsIsCalled_Then_ReturnsCollision() {
		// Given / When
		List<EventType> topics = playerControlSystem.getTopics();

		// Then
		assertEquals(1, topics.size());
		assertEquals(EventType.COLLISION, topics.get(0));
	}

	@Test
	public void Given_PlayerPositionIsRandom_When_resetPlayerPositionIsCalled_Then_PlayerPositionIsResetToCenter() {
		// Given
		player.setX(Math.random() * 100);
		player.setY(Math.random() * 100);
		player.setDX(Math.random() * 100);
		player.setDY(Math.random() * 100);
		world.addEntity(player);

		// When
		playerControlSystem.resetPlayerPosition(gameData, world);

		// Then
		assertEquals(gameData.getDisplayWidth() / 2, player.getX());
		assertEquals(gameData.getDisplayHeight() / 2, player.getY());
		assertEquals(0, player.getDX());
		assertEquals(0, player.getDY());
	}

	@Test
	public void Given_PlayerHasNoHealthLeft_When_processIsCalled_Then_PlayerDoesNotRespawn() {
		// Given
		player.setHealth(0);
		world.removeEntity(player);

		// When
		playerControlSystem.process(gameData, world);

		// Then
		assertFalse(world.getEntities().contains(player));
		assertNull(player.getRespawnTime());
	}

	@Test
	public void Given_PlayerHasHealthAndRespawnTimeIsNull_When_processIsCalled_Then_RespawnTimeIsSet() {
		// Given
		player.setHealth(1);
		world.removeEntity(player);
		assertNull(player.getRespawnTime());

		// When
		playerControlSystem.process(gameData, world);

		// Then
		assertNotNull(player.getRespawnTime());
		assertTrue(player.getRespawnTime().isBefore(LocalTime.now()));
	}

	@Test
	public void Given_PlayerHasHealthAndRespawnTimeIsMoreThan3SecondsAgo_When_processIsCalled_Then_PlayerIsRespawnedAndRespawnTimeIsSetToNull() {
		// Given
		player.setHealth(1);
		player.setRespawnTime(LocalTime.now().minusSeconds(3));
		world.removeEntity(player);

		// When
		playerControlSystem.process(gameData, world);

		// Then
		assertTrue(world.getEntities().contains(player));
		assertEquals(gameData.getDisplayWidth() / 2, player.getX());
		assertEquals(gameData.getDisplayHeight() / 2, player.getY());
		assertEquals(0, player.getDX());
		assertEquals(0, player.getDY());
		assertNull(player.getRespawnTime());
	}

	@Test
	public void Given_PlayerIsMovingOutsideGameArea_When_processIsCalled_Then_PlayerPositionStaysWithinGameArea() {
		// Given
		player.setX(gameData.getDisplayWidth() + 1);
		player.setY(gameData.getDisplayHeight() + 1);

		// When
		playerControlSystem.process(gameData, world);

		// Then
		assertTrue(player.getX() >= 0 && player.getX() <= gameData.getDisplayWidth());
		assertTrue(player.getY() >= 0 && player.getY() <= gameData.getDisplayHeight());
	}

	private static Stream<Integer> provideLeftRightGameKeys() {
		return Stream.of(GameKeys.LEFT, GameKeys.RIGHT);
	}

}
