package demos.tests;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.graphics.Camera;
import game.test.GameObjectAdder;

/**
 * @author Julius Häger
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

		settings.putSetting("UseDefaultKeyBindings", true);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("DebugID", true);
		
		settings.putSetting("GameInit", new Test2());
		
		Game.setup(settings);
		
		Game.run();
		
	}
	
	@Override
	public void initialize(GameSettings settings) {
		GameObjectAdder adder = new GameObjectAdder();
		Game.gameObjectHandler.addGameObject(adder);
	}
}
