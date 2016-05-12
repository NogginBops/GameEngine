package demos.tests;

import java.awt.Color;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.graphics.Camera;
import game.screen.ScreenRect;

public class CameraTest1 implements GameInitializer {

	//TODO: Make this be able to be added on top of another init
	
	public static void main(String[] args) {
		GameSettings settings = GameSettings.getDefaultGameSettings();
		
		settings.putSetting("Name", "Camera Test #1");
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("GameInit", new CameraTest1());
		
		Game game = new Game(settings);
		
		game.run();
		
	}
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		
		Camera camera = settings.getSettingAs("MainCamera", Camera.class);
		
		Camera newCamera = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		newCamera.setScreenRectangle(new ScreenRect(0.59f, 0.59f, 0.4f, 0.4f));
		
		newCamera.setBackgroundColor(new Color(20, 200, 100, 100));
		
		Game.screen.addPainter(newCamera);
		
		Game.gameObjectHandler.addGameObject(newCamera, "Secondary camera");
	}

}
