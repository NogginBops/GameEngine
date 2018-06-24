package game.gameObject.handler.event;

import game.gameObject.GameObject;
import game.gameObject.handler.GameObjectHandler;

/**
 * @author Julius Häger
 *
 */
public class GameObjectDestroyedEvent extends GameObjectEvent {

	//JAVADOC: GameObjectDestryoedEvent
	
	/**
	 * @param origin
	 * @param object
	 */
	public GameObjectDestroyedEvent(GameObjectHandler origin, GameObject object) {
		super(origin, object);
	}

}
