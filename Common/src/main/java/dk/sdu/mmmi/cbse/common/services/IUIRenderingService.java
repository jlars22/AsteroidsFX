package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import javafx.scene.layout.Pane;

public interface IUIRenderingService {
	void generate(Pane gameWindow);
	void update(Pane gameWindow, GameData gameData);
}
