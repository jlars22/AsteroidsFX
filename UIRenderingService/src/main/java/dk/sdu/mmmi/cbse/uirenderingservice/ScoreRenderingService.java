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
		Text text = (Text) gameWindow.lookup("#scoreText");
		if (text != null) {
			text.toFront();
			text.setText(
					String.valueOf(getScoreServices().stream().findFirst().map(IScoreService::getScore).orElse(0)));
		}
	}

	private Collection<? extends IScoreService> getScoreServices() {
		return ServiceLoader.load(IScoreService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
	}
}
