package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.Event.EventType;
import dk.sdu.mmmi.cbse.common.services.IObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBroker {

	private static EventBroker instance;
	private final Map<EventType, List<IObserver>> observers = new HashMap<>();

	public static synchronized EventBroker getInstance() {
		if (instance == null) {
			instance = new EventBroker();
		}
		return instance;
	}

	public void addObserver(List<EventType> topic, IObserver observer) {
		topic.forEach(t -> observers.computeIfAbsent(t, o -> new ArrayList<>()).add(observer));
	}

	public void publish(Event event) {
		List<IObserver> observers = this.observers.get(event.getEventType());
		if (observers != null) {
			observers.forEach(observer -> observer.onEvent(event));
		}
	}
}
