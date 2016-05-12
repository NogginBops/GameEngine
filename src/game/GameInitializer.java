package game;

/**
 * @author Julius H�ger
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
