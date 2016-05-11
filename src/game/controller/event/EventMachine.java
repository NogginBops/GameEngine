package game.controller.event;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author Julius Häger
 *
 */
public class EventMachine {
	
	//JAVADOC: EventMachine

	private ConcurrentSkipListMap<Class<? extends GameEvent<?>>, CopyOnWriteArrayList<EventListener>> eventListenerMap;

	/**
	 * 
	 */
	public EventMachine() {
		eventListenerMap = new ConcurrentSkipListMap<Class<? extends GameEvent<?>>, CopyOnWriteArrayList<EventListener>>(new Comparator<Class<? extends GameEvent<?>>>() {
			@Override
			public int compare(Class<? extends GameEvent<?>> o1, Class<? extends GameEvent<?>> o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	/**
	 * 
	 * @param event
	 * @param listener
	 */
	public void addEventListener(Class<? extends GameEvent<?>> event, EventListener listener) {
		CopyOnWriteArrayList<EventListener> listeners = eventListenerMap.get(event);
		if (listeners == null) {
			eventListenerMap.put(event, listeners = new CopyOnWriteArrayList<EventListener>());
		}
		listeners.add(listener);
	}

	/**
	 * 
	 * @param event
	 * @param listener
	 */
	public void removeEventListener(Class<? extends GameEvent<?>> event, EventListener listener) {
		CopyOnWriteArrayList<EventListener> eventListeners = eventListenerMap.get(event);
		eventListeners.remove(listener);
		if (eventListeners.size() <= 0) {
			eventListenerMap.remove(event);
		}
	}
	
	//NOTE: This method of firing events has O(n^2) time complexity, this might need optimization.
	
	/**
	 * @param event
	 */
	public <T extends GameEvent<?>> void fireEvent(T event) {
		for (Class<? extends GameEvent<?>> eventClass : eventListenerMap.keySet()) {
			if(GameEvent.class.isAssignableFrom(eventClass)){
				for (EventListener eventListener : eventListenerMap.get(eventClass)) {
					eventListener.eventFired(event);
				}
			}
		}
	}
	
	/**
	 * @param event
	 * @param upperLimit
	 */
	public <T extends GameEvent<?>> void fireEvent(T event, Class<? super T> upperLimit) {
		for (Class<? extends GameEvent<?>> eventClass : eventListenerMap.keySet()) {
			if(upperLimit.isAssignableFrom(eventClass)){
				for (EventListener eventListener : eventListenerMap.get(eventClass)) {
					eventListener.eventFired(event);
				}
			}
		}
	}
}
