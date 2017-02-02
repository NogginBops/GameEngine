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

	// JAVADOC: Scene
	
	// TODO: Scene
	
	// TODO: A Scene class is probably not needed
	// There could be metadata associated with a scene, but what data?
	// If name is the only metadata a HashMap could be used instead.
	
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
