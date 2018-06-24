package game.gameObject.handler.event;

import game.gameObject.GameObject;
import game.gameObject.handler.GameObjectHandler;

/**
 * @author Julius H�ger
 *
 */
public class GameObjectCreatedEvent extends GameObjectEvent {
	
	//JAVADOC: GameObjectCreated

	/**
	 * @param origin
	 * @param object
	 */
	public GameObjectCreatedEvent(GameObjectHandler origin, GameObject object) {
		super(origin, object);
	}
	
}
