package game.controller.event;

/**
 * @author Julius H�ger
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
