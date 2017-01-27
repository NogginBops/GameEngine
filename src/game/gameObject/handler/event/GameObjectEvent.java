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
public class GameObjectEvent extends GameEvent {

	
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
	public GameObjectEvent(GameObjectHandler origin, GameObject object) {
		super(origin);
		this.object = object;
	}
	
	@Override
	public String toString() {
		return "GameObjectEvent[Origin: " + origin + ", Object: " + object + "]";
	}
}
