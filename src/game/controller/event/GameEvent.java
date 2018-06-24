package game.controller.event;

/**
 * 
 * Note: Events should be immutable.
 * 
 * @author Julius Häger
 * @param <T> 
 *
 */
public abstract class GameEvent{
	
	//JAVADOC: GameEvent
	
	/**
	 * 
	 */
	public final Object origin;
	
	/**
	 * 
	 * @param origin
	 * @param command
	 */
	public GameEvent(Object origin) {
		this.origin = origin;
	}

	@Override
	public String toString() {
		return "GameEvent[Origin=" + origin + "]";
	}
}
