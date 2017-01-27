package game.controller.event.engineEvents;

import game.Game;
import game.controller.event.GameEvent;

/**
 * 
 * @author Julius Häger
 *
 */
public class GameQuitEvent extends GameEvent {
	
	//NOTE: This might be relocated as a nested class of game.
	
	//JAVADOC: GameQuitEvent
	
	/**
	 * 
	 * @param origin
	 * @param command
	 */
	public GameQuitEvent(Game origin) {
		super(origin);
	}
	
}
