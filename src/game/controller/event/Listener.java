package game.controller.event;

/**
 * @author Julius Häger
 *
 * @param <T>
 * @param <U>
 */
public interface Listener<T extends GameEvent<?>> {
	
	//NOTE: THis is a concept class for the new EventListener inteface

	/**
	 * @param event
	 */
	public void eventFired(T event);

}
