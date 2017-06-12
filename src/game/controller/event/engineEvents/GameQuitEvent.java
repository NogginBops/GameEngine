package game.controller.event.engineEvents;

import game.Game;
import game.controller.event.GameEvent;

/**
 * 
 * @author Julius Häger
 *
 */
public class GameQuitEvent extends GameEvent {
	
	//JAVADOC: GameQuitEvent
	
	/**
	 * 
	 * @param origin
	 */
	public GameQuitEvent(Class<Game> origin) {
		super(origin);
	}
	
}
