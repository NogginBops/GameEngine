package game.controller.event;

public abstract class EventListener {
	
	public abstract <T extends GameEvent> void eventFired(T event);
	
}
