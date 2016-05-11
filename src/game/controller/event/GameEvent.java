package game.controller.event;

/**
 * 
 * Note: Events should be immutable.
 * 
 * @author Julius Häger
 * @param <T> 
 *
 */
public abstract class GameEvent<T>{
	
	//JAVADOC: GameEvent
	
	//TODO: Is one type of gameEvent for one type of origin a good solution?
	/**
	 * 
	 */
	public final T origin;
	
	//TODO: Explore a more fail-safe solution
	/**
	 * <b>NOTE:</b> The class should specify which commands are valid through public static final strings
	 */
	public final String command;

	/**
	 * 
	 * @param origin
	 * @param command
	 */
	public GameEvent(T origin, String command) {
		this.origin = origin;
		this.command = command;
	}

	@Override
	public String toString() {
		return "GameEvent[Origin=" + origin + ", Command: " + command + "]";
	}
}
