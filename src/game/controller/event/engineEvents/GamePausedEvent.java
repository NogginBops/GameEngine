package game.controller.event.engineEvents;

import game.controller.event.GameEvent;

/**
 * @author Julius Häger
 *
 */
public class GamePausedEvent extends GameEvent {

	//JAVADOC: GamePausedEvent
	
	/**
	 * 
	 */
	public final boolean paused;
	
	/**
	 * @param origin
	 * @param paused 
	 */
	public GamePausedEvent(Object origin, boolean paused) {
		super(origin);
		
		this.paused = paused;
	}
}
