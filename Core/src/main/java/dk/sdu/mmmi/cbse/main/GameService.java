package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entitystylingservice.IEntityStylingService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	private final GameData gameData = new GameData();
	private final World world = new World();
	private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
	private final Pane gameWindow = new Pane();
	private final List<IGamePluginService> gamePluginServices;
	private final List<IEntityProcessingService> entityProcessingServices;
	private final List<IPostEntityProcessingService> postEntityProcessingServices;
	private final List<IUIRenderingService> uiRenderingServices;
	private final List<IEntityStylingService> entityStylingServices;

	GameService(List<IGamePluginService> gamePluginServices, List<IEntityProcessingService> entityProcessingServiceList,
			List<IPostEntityProcessingService> postEntityProcessingServices,
			List<IUIRenderingService> uiRenderingServices, List<IEntityStylingService> entityStylingServices) {
		this.gamePluginServices = gamePluginServices;
		this.entityProcessingServices = entityProcessingServiceList;
		this.postEntityProcessingServices = postEntityProcessingServices;
		this.uiRenderingServices = uiRenderingServices;
		this.entityStylingServices = entityStylingServices;
	}

	public void start(Stage window) {
		window.setResizable(false);
		gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());

		Scene scene = getScene();

		for (IUIRenderingService renderingService : uiRenderingServices) {
			renderingService.generate(gameWindow);
		}

		// Lookup all Game Plugins using ServiceLoader
		for (IGamePluginService iGamePlugin : gamePluginServices) {
			iGamePlugin.start(gameData, world);
		}

		for (Entity entity : world.getEntities()) {
			Polygon polygon = new Polygon(entity.getPolygonCoordinates());

			for (IEntityStylingService entityStylingService : entityStylingServices) {
				entityStylingService.styleEntity(entity, polygon);
			}

			polygons.put(entity, polygon);
			gameWindow.getChildren().add(polygon);
		}

		render();

		window.setScene(scene);
		window.setTitle("ASTEROIDS");
		window.show();
	}

	private Scene getScene() {
		Scene scene = new Scene(gameWindow);
		scene.setFill(Color.BLACK);
		scene.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.LEFT)) {
				gameData.getKeys().setKey(GameKeys.LEFT, true);
			}
			if (event.getCode().equals(KeyCode.RIGHT)) {
				gameData.getKeys().setKey(GameKeys.RIGHT, true);
			}
			if (event.getCode().equals(KeyCode.UP)) {
				gameData.getKeys().setKey(GameKeys.UP, true);
			}
			if (event.getCode().equals(KeyCode.SPACE)) {
				gameData.getKeys().setKey(GameKeys.SPACE, true);
			}
		});
		scene.setOnKeyReleased(event -> {
			if (event.getCode().equals(KeyCode.LEFT)) {
				gameData.getKeys().setKey(GameKeys.LEFT, false);
			}
			if (event.getCode().equals(KeyCode.RIGHT)) {
				gameData.getKeys().setKey(GameKeys.RIGHT, false);
			}
			if (event.getCode().equals(KeyCode.UP)) {
				gameData.getKeys().setKey(GameKeys.UP, false);
			}
			if (event.getCode().equals(KeyCode.SPACE)) {
				gameData.getKeys().setKey(GameKeys.SPACE, false);
			}
		});
		return scene;

	}

	void render() {
		new AnimationTimer() {
			private long then = 0;

			@Override
			public void handle(long now) {
				update();
				draw();
				gameData.getKeys().update();
			}
		}.start();
	}

	private void update() {
		// Update
		for (IEntityProcessingService entityProcessorService : entityProcessingServices) {
			entityProcessorService.process(gameData, world);
		}
		for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessingServices) {
			postEntityProcessorService.process(gameData, world);
		}

		for (IUIRenderingService renderingService : uiRenderingServices) {
			renderingService.update(gameWindow, gameData);
		}
	}

	private void draw() {

		for (Entity entity : world.getEntities()) {
			if (!polygons.containsKey(entity)) {
				Polygon polygon = new Polygon(entity.getPolygonCoordinates());

				for (IEntityStylingService entityStylingService : entityStylingServices) {
					entityStylingService.styleEntity(entity, polygon);
				}

				polygons.put(entity, polygon);
				gameWindow.getChildren().add(polygon);
			}
		}

		for (Entity entity : world.getEntities()) {
			Polygon polygon = polygons.get(entity);
			polygon.setTranslateX(entity.getX());
			polygon.setTranslateY(entity.getY());
			polygon.setRotate(entity.getRotation());
		}

		polygons.forEach((key, value) -> {
			if (!world.getEntities().contains(key)) {
				gameWindow.getChildren().remove(value);
				polygons.remove(key);
			}
		});

	}

}
