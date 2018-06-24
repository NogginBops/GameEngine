package game.controller.event;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Julius Häger
 *
 */
public class EventMachine {
	
	//JAVADOC: EventMachine
	
	//TODO: Event machine debug frame
	
	private ConcurrentSkipListMap<Class<? extends GameEvent>, CopyOnWriteArrayList<EventListener<? extends GameEvent>>> eventListenerMap = new ConcurrentSkipListMap<Class<? extends GameEvent>, CopyOnWriteArrayList<EventListener<? extends GameEvent>>>((o1, o2) -> o1.getName().compareTo(o2.getName()));
		
	//NOTE: Is 5 threads optimal? 
	//Should the executor be able to allocate threads? 
	//What should happen if no threads are available?
	private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5, Executors.defaultThreadFactory());
	
	/**
	 * 
	 */
	public EventMachine() {
		
	}
	
	/**
	 * 
	 * @param <T> 
	 * @param event
	 * @param listener
	 */
	public <T extends GameEvent> void addEventListener(Class<T> event, EventListener<T> listener) {
		CopyOnWriteArrayList<EventListener<? extends GameEvent>> listeners = eventListenerMap.get(event);
		if (listeners == null) {
			eventListenerMap.put(event, listeners = new CopyOnWriteArrayList<EventListener<? extends GameEvent>>());
		}
		listeners.add(listener);
	}

	/**
	 * 
	 * @param <T> 
	 * @param event
	 * @param listener
	 */
	public <T extends GameEvent> void removeEventListener(Class<T> event, EventListener<T> listener) {
		CopyOnWriteArrayList<EventListener<?>> eventListeners = eventListenerMap.get(event);
		eventListeners.remove(listener);
		if (eventListeners.size() <= 0) {
			eventListenerMap.remove(event);
		}
	}
	
	//NOTE: This method of firing events has O(n^2) time complexity, this might need optimization.
	
	//NOTE: Should all events be executed on another thread? Should there be a flag to expose this option?
	
	/**
	 * @param <T> 
	 * @param event
	 * @param eventClass 
	 */
	@SuppressWarnings("unchecked")
	public <T extends GameEvent> void fireEvent(T event) {
		executor.execute(() -> {
			if (eventListenerMap.containsKey(event.getClass())) {
				for (EventListener<?> eventListener : eventListenerMap.get(event.getClass())) {
					((EventListener<T>)eventListener).eventFired(event);
				}
			}
		});
	}
	
	//NOTE: Should the timeUnit parameter on executor.schedule(Runnable r, long delay, TimeUnit timeUnit) be exposed?
	
	/**
	 * @param <T> 
	 * @param event
	 * @param eventClass 
	 * @param delayMillis
	 */
	public <T extends GameEvent> void fireEvent(T event, long delayMillis) {
		executor.schedule(() -> { fireEvent(event); }, delayMillis, TimeUnit.MILLISECONDS);
	}
}
