package game;

/**
 * @author Julius Häger
 *
 */
public interface GameInitializer {
	
	//JAVADOC: GameInitializer
	
	/**
	 * @param game
	 * @param settings
	 */
	public void initialize(Game game, GameSettings settings);

}
