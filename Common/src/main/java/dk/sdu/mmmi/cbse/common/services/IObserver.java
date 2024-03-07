package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.Event.EventType;
import java.util.List;

public interface IObserver {
	void onEvent(Event event);

	List<EventType> getTopics();
}
