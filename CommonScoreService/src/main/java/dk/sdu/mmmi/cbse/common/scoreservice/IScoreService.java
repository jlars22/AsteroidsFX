package dk.sdu.mmmi.cbse.common.scoreservice;

import dk.sdu.mmmi.cbse.common.data.Entity;

public interface IScoreService {

	void addScore(Entity entity);

	int getScore();

	int getLevel();

}
