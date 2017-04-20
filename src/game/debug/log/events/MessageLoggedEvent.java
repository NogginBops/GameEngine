package game.debug.log.events;

import game.controller.event.GameEvent;
import game.debug.log.LogMessage;

public class MessageLoggedEvent extends GameEvent {

	public final LogMessage message;
	
	public MessageLoggedEvent(Object origin, LogMessage message) {
		super(origin);
		this.message = message;
	}
}
