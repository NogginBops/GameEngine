package game.util;

import game.Game;
import game.gameObject.handler.event.GameObjectCreatedEvent;
import game.gameObject.handler.event.GameObjectDestroyedEvent;

/**
 * @author Julius Häger
 *
 */
public class StandardUpdater extends Updater {
	
	//JAVADOC: StandardUpdater
	
	/**
	 * 
	 */
	public StandardUpdater() {
		super();
		
		Game.eventMachine.addEventListener(GameObjectCreatedEvent.class, (event) -> { if (event.object instanceof UpdateListener) listeners.add((UpdateListener)event.object); });
		Game.eventMachine.addEventListener(GameObjectDestroyedEvent.class, (event) -> { if (event.object instanceof UpdateListener) listeners.remove((UpdateListener)event.object); });
	}
}
