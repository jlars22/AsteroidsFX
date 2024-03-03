package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Event;
import dk.sdu.mmmi.cbse.common.data.Event.EventType;

public interface IObserver {
	void onEvent(Event event);

	EventType getTopic();
}
