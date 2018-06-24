package game.controller.event;

/**
 * 
 * @author Julius H�ger
 * @param <T> 
 *
 */
public interface EventListener<T extends GameEvent> {
	
	//JAVADOC: EventListener

	/**
	 * @param event
	 */
	public void eventFired(T event);

}
