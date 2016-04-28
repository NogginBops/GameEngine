package game.controller.event.engineEvents;

import game.Game;
import game.controller.event.GameEvent;

/**
 * 
 * @author Julius Häger
 *
 */
public class GameStartEvent extends GameEvent<Game> {

	/**
	 * 
	 * @param origin
	 * @param command
	 */
	public GameStartEvent(Game origin, String command) {
		super(origin, command);
	}
}
