package game.controller.event;

/**
 * 
 * @author Julius Häger
 *
 */
public interface EventListener {

	/**
	 * @param event
	 */
	public <T extends GameEvent<?>> void eventFired(T event);

}
