package game.controller.event;

/**
 * 
 * @author Julius H�ger
 *
 */
public interface EventListener {
	
	//JAVADOC: EventListener

	/**
	 * @param event
	 */
	public <T extends GameEvent<?>> void eventFired(T event);

}
