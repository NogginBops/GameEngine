package demos.tests;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.graphics.Camera;
import game.test.GameObjectAdder;

/**
 * @author Julius H�ger
 *
 */
public class Test2 implements GameInitializer {

	//JAVADOC: Test2
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "Test #2");
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("DebugID", true);
		
		settings.putSetting("GameInit", new Test2());
		
		Game game = new Game(settings);
		
		game.run();
		
	}
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		GameObjectAdder adder = new GameObjectAdder();
		Game.gameObjectHandler.addGameObject(adder);
	}
}
