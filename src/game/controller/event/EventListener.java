package game.controller.event;

/**
 * 
 * @author Julius H�ger
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
