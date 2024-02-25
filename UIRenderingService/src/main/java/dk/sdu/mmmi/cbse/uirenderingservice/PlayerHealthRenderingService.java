package dk.sdu.mmmi.cbse.uirenderingservice;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PlayerHealthRenderingService implements IUIRenderingService {

	@Override
	public Text generate() {
		Text text = new Text(10, 20, "");
		text.setFont(new Font(30));
		text.setStroke(Color.WHITE);
		text.setFill(Color.RED);
		text.setStrokeWidth(1);
		text.setStrokeType(StrokeType.OUTSIDE);
		text.setId("playerHealthText");
		return text;
	}

	@Override
	public void update(Pane gameWindow, GameData gameData) {
		Text text = (Text) gameWindow.lookup("#playerHealthText");
		if (text != null) {
			text.toFront();
			String currentHearts = text.getText();
			String newHearts = generateHearts(gameData.getPlayer().getHealth());
			if (!currentHearts.equals(newHearts)) {
				flashText(text);
			}
			text.setText(newHearts);
		}
	}

	private void flashText(Text text) {
		Timeline timeline = new Timeline();
		KeyValue kv1 = new KeyValue(text.opacityProperty(), 1.0);
		KeyValue kv2 = new KeyValue(text.opacityProperty(), 0.0);
		KeyValue kv3 = new KeyValue(text.opacityProperty(), 1.0);
		KeyFrame kf1 = new KeyFrame(Duration.millis(0), kv1);
		KeyFrame kf2 = new KeyFrame(Duration.millis(100), kv2);
		KeyFrame kf3 = new KeyFrame(Duration.millis(200), kv3);
		KeyFrame kf4 = new KeyFrame(Duration.millis(300), kv2);
		KeyFrame kf5 = new KeyFrame(Duration.millis(400), kv3);
		KeyFrame kf6 = new KeyFrame(Duration.millis(500), kv2);
		KeyFrame kf7 = new KeyFrame(Duration.millis(600), kv3);
		timeline.getKeyFrames().addAll(kf1, kf2, kf3, kf4, kf5, kf6, kf7);
		timeline.play();
	}

	private String generateHearts(int health) {
		return "❤ ".repeat(Math.max(0, health));
	}

}
