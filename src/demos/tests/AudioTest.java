package demos.tests;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.graphics.Camera;
import game.sound.AudioEngine;
import game.test.GameObjectAdderWithAudio;

/**
 * @author Julius Häger
 *
 */
public class AudioTest implements GameInitializer {

	//JAVADOC: AudioTest
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "AudioTest #1");
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("GameInit", new AudioTest());
		
		Game game = new Game(settings);
		
		game.run();
		
	}
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		GameObjectAdderWithAudio adder = new GameObjectAdderWithAudio(-5, -5);
		Game.gameObjectHandler.addGameObject(adder);

		AudioEngine.setAudioListener(adder);
	}

}
