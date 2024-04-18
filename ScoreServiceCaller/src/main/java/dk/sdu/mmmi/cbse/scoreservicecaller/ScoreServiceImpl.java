package dk.sdu.mmmi.cbse.scoreservicecaller;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.scoreservice.IScoreService;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScoreServiceImpl implements IScoreService, IObserver {

	private final Set<String> scoredEntities = new HashSet<>();
	private final HttpClient client = HttpClient.newHttpClient();

	@Override
	public void addScore(Entity entity) {
		String url;
		if (entity.getType() == Entity.Type.ENEMY) {
			url = "http://localhost:8080/score?score=200";
		} else if (entity.getType() == Entity.Type.ASTEROID) {
			int score = 160 - 40 * entity.getHealth();
			url = "http://localhost:8080/score?score=" + score;
		} else {
			return;
		}

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).PUT(HttpRequest.BodyPublishers.noBody())
				.build();

		try {
			client.send(request, HttpResponse.BodyHandlers.discarding());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getScore() {
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/score")).build();

		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return Integer.parseInt(response.body());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public void onEvent(Event event) {
		incrementScore(event);
	}

	private void incrementScore(Event event) {
		Entity entityA = event.getEntityA();
		Entity entityB = event.getEntityB();

		if (entityA.getType() == Entity.Type.ENEMY || entityA.getType() == Entity.Type.ASTEROID) {
			if (!scoredEntities.contains(entityA.getID())) {
				addScore(entityA);
				scoredEntities.add(entityA.getID());
			}
		}
		if (entityB.getType() == Entity.Type.ENEMY || entityB.getType() == Entity.Type.ASTEROID) {
			if (!scoredEntities.contains(entityB.getID())) {
				addScore(entityB);
				scoredEntities.add(entityB.getID());
			}
		}
	}

	@Override
	public List<Event.EventType> getTopics() {
		return List.of(Event.EventType.SCORE_INCREMENT, Event.EventType.NEW_LEVEL);
	}
}