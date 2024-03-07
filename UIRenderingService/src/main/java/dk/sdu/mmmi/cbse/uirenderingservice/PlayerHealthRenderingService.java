package dk.sdu.mmmi.cbse.uirenderingservice;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PlayerHealthRenderingService implements IUIRenderingService {

	@Override
	public void generate(Pane gameWindow) {

	}

	private ImageView generatePixelHeart() {
		Image heartImage = new Image(getClass().getResource("/pixelheart.png").toExternalForm());
		ImageView heartImageView = new ImageView(heartImage);
		heartImageView.setFitHeight(40);
		heartImageView.setPreserveRatio(true);
		return heartImageView;
	}

	@Override
	public void update(Pane gameWindow, GameData gameData) {
		gameWindow.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("pixelHeart"));

		for (int i = 0; i < gameData.getPlayer().getHealth(); i++) {
			ImageView pixelHeart = generatePixelHeart();
			pixelHeart.setId("pixelHeart" + i);

			double padding = 10.0;
			double heartWidth = pixelHeart.getBoundsInLocal().getWidth();
			double xPos = gameWindow.getWidth() - (i + 1) * heartWidth - padding;
			pixelHeart.setLayoutX(xPos);

			gameWindow.getChildren().add(pixelHeart);
		}
	}
}