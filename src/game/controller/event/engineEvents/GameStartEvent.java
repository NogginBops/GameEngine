package game.controller.event.engineEvents;

import game.Game;
import game.controller.event.GameEvent;

/**
 * 
 * @author Julius H�ger
 *
 */
public class GameStartEvent extends GameEvent {

	//JAVADOC: GameStartEvent
	
	/**
	 * 
	 * @param origin
	 */
	public GameStartEvent(Game origin) {
		super(origin);
	}
}
