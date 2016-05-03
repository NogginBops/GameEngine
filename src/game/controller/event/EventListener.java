package game.controller.event;

/**
 * 
 * @author Julius Häger
 *
 */
public interface EventListener {
	
	//JAVADOC: EventListener

	/**
	 * @param event
	 */
	public <T extends GameEvent<?>> void eventFired(T event);

}
