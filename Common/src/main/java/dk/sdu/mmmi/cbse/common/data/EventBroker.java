package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.services.IObserver;
import java.util.ArrayList;
import java.util.List;

public class EventBroker {
	private static EventBroker instance;
	private final List<IObserver> observers = new ArrayList<>();

	public static synchronized EventBroker getInstance() {
		if (instance == null) {
			instance = new EventBroker();
		}
		return instance;
	}

	public void addObserver(IObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers(Event event) {
		for (IObserver observer : observers) {
			observer.onEvent(event);
		}
	}
}
