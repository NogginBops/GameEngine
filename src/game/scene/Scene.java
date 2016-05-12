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

	// JAVADOC: Scene
	
	//NOTE: Scene initializer?
	private GameInitializer sceneInit;
	
	public Scene(GameInitializer sceneInit) {
		this.sceneInit = sceneInit;
	}
	
	public void load(Game game, GameSettings settings){
		sceneInit.initialize(game, settings);
	}
	
}
