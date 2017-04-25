package demos.tests;

import java.nio.file.Paths;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.graphics.Camera;
import game.test.TestSprite;

public class KeyBindingTest {

	public static void main(String[] args) {
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("GameInit", new GameInitializer() {
			@Override
			public void initialize(Game game, GameSettings settings) {
				Game.keyHandler.parseKeyBindings(Paths.get("./res/DefaultKeyBindings.txt"));
				
				Game.gameObjectHandler.addGameObject(new TestSprite(0, 0, 10, 10));
			}
		});
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);
		
		Game game = new Game(settings);
		
		game.run();
	}
}
