package game.gameObject.handler.event;

import game.gameObject.GameObject;
import game.gameObject.handler.GameObjectHandler;

/**
 * @author Julius H�ger
 *
 */
public class GameObjectDestryoedEvent extends GameObjectEvent {

	//JAVADOC: GameObjectDestryoedEvent
	
	/**
	 * @param origin
	 * @param object
	 */
	public GameObjectDestryoedEvent(GameObjectHandler origin, GameObject object) {
		super(origin, "Destroyed", object);
	}

}
