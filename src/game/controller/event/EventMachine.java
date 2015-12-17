package game.controller.event;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventMachine {

	public ConcurrentSkipListMap<Class<? extends GameEvent>, CopyOnWriteArrayList<EventListener>> eventListenerMap;

	public EventMachine() {
		eventListenerMap = new ConcurrentSkipListMap<>();
	}

	public void addEventListener(Class<? extends GameEvent> event, EventListener listener) {
		CopyOnWriteArrayList<EventListener> listeners = eventListenerMap.get(event);
		if (listeners == null) {
			eventListenerMap.put(event, listeners = new CopyOnWriteArrayList<EventListener>());
		}
		listeners.add(listener);
	}

	public void removeEventListener(Class<? extends GameEvent> event, EventListener listener) {
		CopyOnWriteArrayList<EventListener> eventListeners = eventListenerMap.get(event);
		eventListeners.remove(listener);
		if (eventListeners.size() <= 0) {
			eventListenerMap.remove(event);
		}
	}

	public <T extends GameEvent> void fireEvent(T event) {
		CopyOnWriteArrayList<EventListener> listeners = eventListenerMap.get(event);
		for (EventListener listener : listeners) {
			listener.eventFired(event);
		}
	}
}
