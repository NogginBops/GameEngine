package game.debug.event;

import game.controller.event.GameEvent;

/**
 * @author Julius Häger
 *
 */
public class DebugObjectSelectedEvent extends GameEvent {
	
	// JAVADOC: DebugGameObjectSelectedEvent
	
	/**
	 * 
	 */
	public final Object object;
	
	/**
	 * @param origin
	 * @param obj
	 */
	public DebugObjectSelectedEvent(Object origin, Object obj) {
		super(origin);
		this.object = obj;
	}
}
