package dk.sdu.mmmi.cbse.uirenderingservice;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PlayerHealthRenderingService implements IUIRenderingService {

	@Override
	public void generate(Pane gameWindow) {

	}

	private Polygon generateMiniPlayer() {
		Polygon playerPolygon = new Polygon();
		playerPolygon.getPoints().addAll(-10.0, -10.0, 20.0, 0.0, -10.0, 10.0);
		playerPolygon.setStroke(Color.WHITE);
		playerPolygon.setStrokeWidth(2);
		playerPolygon.setRotate(-90);
		playerPolygon.setScaleX(0.7);
		playerPolygon.setScaleY(0.7);
		playerPolygon.setLayoutY(50);
		return playerPolygon;
	}

	@Override
	public void update(Pane gameWindow, GameData gameData) {
		gameWindow.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("miniPlayer"));

		for (int i = 0; i < gameData.getPlayer().getHealth(); i++) {
			Polygon miniPlayer = generateMiniPlayer();
			miniPlayer.setId("miniPlayer" + i);
			miniPlayer.setLayoutX(14 + i * 25); // adjust the position
			gameWindow.getChildren().add(miniPlayer);
		}
	}

}
