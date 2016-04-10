package game.controller.event;

/**
 * 
 * @author Julius Häger
 *
 * @param <T>
 */
public interface EventListener {

	/**
	 * 
	 * @param event
	 */
	public <T extends GameEvent<?>> void eventFired(T event);

}
