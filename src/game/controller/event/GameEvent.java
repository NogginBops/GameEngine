package game.controller.event;

/**
 * @author Julius H�ger
 *
 */
public abstract class GameEvent<T>{
	
	//JAVADOC: GameEvent
	
	/**
	 * 
	 */
	public final T origin;
	
	//TODO: Explore a safer solution
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
