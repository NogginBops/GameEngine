package game.gameObject.handler.event;

import game.controller.event.GameEvent;
import game.gameObject.GameObject;
import game.gameObject.handler.GameObjectHandler;

/**
 * 
 * 
 * @author Julius Häger
 *
 */
public class GameObjectEvent extends GameEvent<GameObjectHandler> {

	
	//JAVADOC: GameObjectEvent
	
	/**
	 * 
	 */
	public final GameObject object;
	
	/**
	 * @param origin
	 * @param command
	 * @param object
	 */
	public GameObjectEvent(GameObjectHandler origin, String command, GameObject object) {
		super(origin, command);
		this.object = object;
	}
	
	@Override
	public String toString() {
		return "GameObjectEvent[Origin: " + origin + ", Command: " + command + ", Object: " + object + "]";
	}
}
