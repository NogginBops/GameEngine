package game;

/**
 * @author Julius Häger
 *
 */
public interface GameInitializer {
	
	//JAVADOC: GameInitializer
	
	//TODO: Should this be named Scene initializer?
	
	/**
	 * @param game
	 * @param settings
	 */
	public void initialize(Game game, GameSettings settings);

}
