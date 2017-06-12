package game;

/**
 * @author Julius Häger
 *
 */
public interface GameInitializer {
	
	//JAVADOC: GameInitializer
	
	//TODO: Should this be named Scene initializer?
	
	//TODO: Game is most likely going to be completely static so it wont be needed as a param
	
	/**
	 * @param settings
	 */
	public void initialize(GameSettings settings);

}
