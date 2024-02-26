package dk.sdu.mmmi.cbse.main;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EventBroker;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.entitystylingservice.IEntityStylingService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class Main extends Application {

	private final GameData gameData = new GameData();
	private final World world = new World();
	private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
	private final Pane gameWindow = new Pane();
	private final EventBroker eventBroker = EventBroker.getInstance();

	public static void main(String[] args) {
		launch(Main.class);
	}

	@Override
	public void start(Stage window) throws Exception {
		window.setResizable(false);
		gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());

		Scene scene = getScene();

		for (IUIRenderingService renderingService : getUIRenderingServices()) {
			renderingService.generate(gameWindow);
		}

		// Lookup all Game Plugins using ServiceLoader
		for (IGamePluginService iGamePlugin : getPluginServices()) {
			iGamePlugin.start(gameData, world);
		}

		for (Entity entity : world.getEntities()) {
			Polygon polygon = new Polygon(entity.getPolygonCoordinates());

			for (IEntityStylingService entityStylingService : getEntityStylingServices()) {
				entityStylingService.styleEntity(entity, polygon);
			}

			polygons.put(entity, polygon);
			gameWindow.getChildren().add(polygon);
		}

		render();
		loadObservers();

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

	private void render() {
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
		for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
			entityProcessorService.process(gameData, world);
		}
		for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
			postEntityProcessorService.process(gameData, world);
		}

		for (IUIRenderingService renderingService : getUIRenderingServices()) {
			renderingService.update(gameWindow, gameData);
		}
	}

	private void draw() {

		for (Entity entity : world.getEntities()) {
			if (!polygons.containsKey(entity)) {
				Polygon polygon = new Polygon(entity.getPolygonCoordinates());

				for (IEntityStylingService entityStylingService : getEntityStylingServices()) {
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

	private void loadObservers() {
		ServiceLoader.load(IObserver.class).forEach(eventBroker::addObserver);
	}

	private Collection<? extends IGamePluginService> getPluginServices() {
		return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}

	private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
		return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get)
				.collect(toList());
	}

	private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
		return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get)
				.collect(toList());
	}

	private Collection<? extends IUIRenderingService> getUIRenderingServices() {
		return ServiceLoader.load(IUIRenderingService.class).stream().map(ServiceLoader.Provider::get)
				.collect(toList());
	}

	private Collection<? extends IEntityStylingService> getEntityStylingServices() {
		return ServiceLoader.load(IEntityStylingService.class).stream().map(ServiceLoader.Provider::get)
				.collect(toList());
	}
}
