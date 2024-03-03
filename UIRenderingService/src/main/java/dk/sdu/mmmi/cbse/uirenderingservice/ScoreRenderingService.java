package dk.sdu.mmmi.cbse.uirenderingservice;

import static java.util.stream.Collectors.toList;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.uirenderingservice.IUIRenderingService;
import java.util.Collection;
import java.util.ServiceLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreRenderingService implements IUIRenderingService {

	@Override
	public void generate(Pane gameWindow) {
		Text text = new Text(10, 30, "");
		text.setFont(new Font(30));
		text.setFill(Color.WHITE);
		text.setId("scoreText");
		gameWindow.getChildren().add(text);
	}

	@Override
	public void update(Pane gameWindow, GameData gameData) {
		if (gameData.getPlayer().getHealth() == 0) {
			Text text = (Text) gameWindow.lookup("#scoreText");
			if (text != null) {
				gameWindow.getChildren().remove(text);
			}
			displayGameOverText(gameWindow);
		}

		Text text = (Text) gameWindow.lookup("#scoreText");
		if (text != null) {
			text.toFront();
			text.setText(
					String.valueOf(getScoreServices().stream().findFirst().map(IScoreService::getScore).orElse(0)));
		}
	}

	private void displayGameOverText(Pane gameWindow) {
		Text gameOverText = new Text("GAME OVER");
		gameOverText.setFont(new Font(30));
		gameOverText.setFill(Color.RED);
		gameOverText.toFront();

		double centerX = gameWindow.getWidth() / 2 - gameOverText.getLayoutBounds().getWidth() / 2;
		double centerY = gameWindow.getHeight() / 2 - gameOverText.getLayoutBounds().getHeight() / 2;
		gameOverText.setTranslateX(centerX);
		gameOverText.setTranslateY(centerY);

		Text scoreText = new Text(
				String.valueOf(getScoreServices().stream().findFirst().map(IScoreService::getScore).orElse(0)));
		scoreText.setFont(new Font(20));
		scoreText.setFill(Color.WHITE);
		scoreText.toFront();

		double scoreTextCenterX = gameWindow.getWidth() / 2 - scoreText.getLayoutBounds().getWidth() / 2;
		double scoreTextCenterY = centerY + 30;

		scoreText.setTranslateX(scoreTextCenterX);
		scoreText.setTranslateY(scoreTextCenterY);

		gameWindow.getChildren().addAll(gameOverText, scoreText);
	}

	private Collection<? extends IScoreService> getScoreServices() {
		return ServiceLoader.load(IScoreService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}
}
