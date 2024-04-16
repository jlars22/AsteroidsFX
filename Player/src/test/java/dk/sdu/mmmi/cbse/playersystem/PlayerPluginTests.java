package dk.sdu.mmmi.cbse.playersystem;

import static org.junit.jupiter.api.Assertions.*;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerPluginTests {

	private PlayerPlugin playerPlugin;
	private GameData gameData;
	private World world;

	@BeforeEach
	public void setUp() {
		playerPlugin = new PlayerPlugin();
		gameData = new GameData();
		world = new World();
	}

	@Test
	public void Given_GameDataAndWorld_When_startIsCalled_Then_PlayerIsAddedToWorldAndSetInGameData() {
		// Given / When
		playerPlugin.start(gameData, world);

		// Then
		assertTrue(world.getEntities().stream().anyMatch(entity -> entity instanceof Player));
		assertNotNull(gameData.getPlayer());
		assertEquals(gameData.getDisplayWidth() / 2, gameData.getPlayer().getX());
		assertEquals(gameData.getDisplayHeight() / 2, gameData.getPlayer().getY());
	}

	@Test
	public void Given_GameDataAndWorldWithPlayer_When_stopIsCalled_Then_PlayerIsRemovedFromWorld() {
		// Given
		Entity player = new Player();
		world.addEntity(player);

		// When
		playerPlugin.stop(gameData, world);

		// Then
		assertFalse(world.getEntities().contains(player));
	}
}
