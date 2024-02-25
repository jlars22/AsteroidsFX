package dk.sdu.mmmi.cbse.common.uirenderingservice;

import dk.sdu.mmmi.cbse.common.data.GameData;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public interface IUIRenderingService {
	Text generate();
	void update(Pane gameWindow, GameData gameData);
}
