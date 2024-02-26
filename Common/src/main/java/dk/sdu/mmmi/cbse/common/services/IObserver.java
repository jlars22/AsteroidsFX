package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Event;

public interface IObserver {
	void onEvent(Event event);
}
