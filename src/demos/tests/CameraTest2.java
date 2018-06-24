package demos.tests;

import java.awt.Color;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.graphics.Camera;
import game.screen.ScreenRect;

/**
 * @author Julius Häger
 *
 */
public class CameraTest2 implements GameInitializer {

	//JAVADOC: CameraTest2
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "Camera Test #2");

		settings.putSetting("UseDefaultKeyBindings", true);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("GameInit", new CameraTest2());
		
		Game.setup(settings);
		
		Game.run();
	}
	
	@Override
	public void initialize(GameSettings settings) {
		
		Camera camera = settings.getSettingAs("MainCamera", Camera.class);
		
		Camera Q1 = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		Q1.setScreenRectangle(new ScreenRect(0, 0, 0.5f, 0.5f));
		
		Q1.setBackgroundColor(Color.CYAN);
		
		Q1.receiveKeyboardInput(true);
		
		Game.screen.addPainter(Q1);
		
		Game.gameObjectHandler.addGameObject(Q1, "Q1 camera");
		
		Camera Q2 = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		Q2.setScreenRectangle(new ScreenRect(0.5f, 0, 0.5f, 0.5f));
		
		Q2.setBackgroundColor(Color.MAGENTA);
		
		Q2.receiveKeyboardInput(true);
		
		Game.screen.addPainter(Q2);
		
		Game.gameObjectHandler.addGameObject(Q2, "Q2 camera");
		
		Camera Q3 = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		Q3.setScreenRectangle(new ScreenRect(0, 0.5f, 0.5f, 0.5f));
		
		Q3.setBackgroundColor(Color.YELLOW);
		
		Q3.receiveKeyboardInput(true);
		
		Game.screen.addPainter(Q3);
		
		Game.gameObjectHandler.addGameObject(Q3, "Q3 camera");
		
		Camera Q4 = new Camera(0, 0, camera.getWidth(), camera.getHeight());
		
		Q4.setScreenRectangle(new ScreenRect(0.5f, 0.5f, 0.5f, 0.5f));
		
		Q4.setBackgroundColor(Color.BLACK);
		
		Q4.receiveKeyboardInput(true);
		
		Game.screen.addPainter(Q4);
		
		Game.gameObjectHandler.addGameObject(Q4, "Q4 camera");
	}

}
