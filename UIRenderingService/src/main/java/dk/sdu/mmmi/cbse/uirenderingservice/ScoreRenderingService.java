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
		Text scoreText = getScoreText();
		Text levelText = getLevelText();

		gameWindow.getChildren().addAll(scoreText, levelText);
	}



	@Override
	public void update(Pane gameWindow, GameData gameData) {
		if (isGameOver(gameData)) {
			removeLevelAndScoreText(gameWindow);
			displayGameOverText(gameWindow);
		}

		Text scoreText = (Text) gameWindow.lookup("#scoreText");
		Text levelText = (Text) gameWindow.lookup("#levelText");
		if (scoreText != null && levelText != null) {
			updateLevelAndScoreText(scoreText, levelText);
		}
	}

	private  Text getLevelText() {
		Text levelText = new Text(10, 30, "");
		Font font = Font.loadFont(getClass().getResource("/pixelfont.ttf").toExternalForm(), 20);
		levelText.setFont(font);
		levelText.setFill(Color.WHITE);
		levelText.setId("levelText");
		return levelText;
	}

	private Text getScoreText() {
		Text scoreText = new Text(10, 60, "LEVEL 0");
		Font font = Font.loadFont(getClass().getResource("/pixelfont.ttf").toExternalForm(), 20);
		scoreText.setFont(font);
		scoreText.setFill(Color.WHITE);
		scoreText.setId("scoreText");
		return scoreText;
	}

	private void updateLevelAndScoreText(Text scoreText, Text levelText) {
		scoreText.toFront();
		levelText.toFront();
		scoreText.setText(
				String.valueOf(getScoreServices().stream().findFirst().map(IScoreService::getScore).orElse(0)));
		levelText.setText(
				"LEVEL " + getScoreServices().stream().findFirst().map(IScoreService::getLevel).orElse(0));
	}

	private void removeLevelAndScoreText(Pane gameWindow) {
		Text scoreText = (Text) gameWindow.lookup("#scoreText");
		Text levelText = (Text) gameWindow.lookup("#levelText");
		if (scoreText != null && levelText != null) {
			gameWindow.getChildren().remove(scoreText);
			gameWindow.getChildren().remove(levelText);
		}
	}

	private boolean isGameOver(GameData gameData) {
		return gameData.getPlayer().getHealth() == 0;
	}

	private void displayGameOverText(Pane gameWindow) {
		Text gameOverText = new Text("GAME OVER");
		Font gameOverFont = Font.loadFont(getClass().getResource("/pixelfont.ttf").toExternalForm(), 30);
		gameOverText.setFont(gameOverFont);
		gameOverText.setFill(Color.RED);
		gameOverText.toFront();

		double centerX = gameWindow.getWidth() / 2 - gameOverText.getLayoutBounds().getWidth() / 2;
		double centerY = gameWindow.getHeight() / 2 - gameOverText.getLayoutBounds().getHeight() / 2;
		gameOverText.setTranslateX(centerX);
		gameOverText.setTranslateY(centerY);

		Text scoreText = new Text("SCORE " +
				getScoreServices().stream().findFirst().map(IScoreService::getScore).orElse(0));
		Font font = Font.loadFont(getClass().getResource("/pixelfont.ttf").toExternalForm(), 20);
		scoreText.setFont(font);
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
