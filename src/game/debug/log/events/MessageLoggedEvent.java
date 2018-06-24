package game.debug.log.events;

import game.controller.event.GameEvent;
import game.debug.log.LogMessage;

/**
 * @author Julius Häger
 *
 */
public class MessageLoggedEvent extends GameEvent {
	
	// JAVADOC: MessageLoggedEvent
	
	/**
	 * 
	 */
	public final LogMessage message;
	
	/**
	 * @param origin
	 * @param message
	 */
	public MessageLoggedEvent(Object origin, LogMessage message) {
		super(origin);
		this.message = message;
	}
}
