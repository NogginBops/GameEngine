/**
 * 
 */
package game.controller.event.engineEvents;

import game.controller.event.GameEvent;

/**
 * @author Julius Häger
 *
 */
public class SceneLoadedEvent extends GameEvent {

	//JAVADOC: SceneLoadedEvent
	
	/**
	 * @param origin
	 */
	public SceneLoadedEvent(Object origin) {
		super(origin);
	}
}
