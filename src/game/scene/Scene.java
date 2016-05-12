package game.scene;

import game.Game;
import game.GameInitializer;
import game.GameSettings;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class Scene {

	// TODO: Scene
	
	// TODO: A Scene class is probably not needed
	
	// JAVADOC: Scene
	
	//NOTE: Scene initializer?
	private GameInitializer sceneInit;
	
	/**
	 * @param sceneInit
	 */
	public Scene(GameInitializer sceneInit) {
		this.sceneInit = sceneInit;
	}
	
	/**
	 * @param game
	 * @param settings
	 */
	public void load(Game game, GameSettings settings){
		sceneInit.initialize(game, settings);
	}
	
}
