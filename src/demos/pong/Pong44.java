package demos.pong;

import java.awt.event.KeyEvent;

import demos.pong.Pad.Side;
import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.graphics.Camera;
import game.screen.ScreenManager;

/**
 * @author Julius H�ger
 *
 */
public class Pong44 implements GameInitializer {
	
	//JAVADOC: Pong44
	
	private static Camera camera;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.getDefaultGameSettings();
		
		settings.putSetting("Name", "Pong44");
		
		camera = settings.getSettingAs("MainCamera", Camera.class);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("GameInit", new Pong44());
		
		Game game = new Game(settings);
		
		game.run();	
	}

	@Override
	public void initialize(Game game, GameSettings settings) {

		Pad rightPad = new Pad(ScreenManager.getWidth() - 50, 40, 10, 50, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
				camera.getBounds(), Side.RIGHT);

		Game.gameObjectHandler.addGameObject(rightPad, "RightPad");

		Pad rightPad2 = new Pad(ScreenManager.getWidth() - 100, 40, 10, 50, KeyEvent.VK_O, KeyEvent.VK_L,
				camera.getBounds(), Side.RIGHT);

		Game.gameObjectHandler.addGameObject(rightPad2, "RightPad2");

		Pad leftPad = new Pad(50, 40, 10, 50, KeyEvent.VK_W, KeyEvent.VK_S, camera.getBounds(), Side.LEFT);

		Game.gameObjectHandler.addGameObject(leftPad, "LeftPad");

		Pad leftPad2 = new Pad(100, 40, 10, 50, KeyEvent.VK_T, KeyEvent.VK_G, camera.getBounds(), Side.LEFT);

		Game.gameObjectHandler.addGameObject(leftPad2, "LeftPad2");

		Ball ball = new Ball(ScreenManager.getWidth() / 2 - 8, ScreenManager.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		Game.gameObjectHandler.addGameObject(ball, "Ball");

		Ball ball2 = new Ball(ScreenManager.getWidth() / 2 - 8, ScreenManager.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		Game.gameObjectHandler.addGameObject(ball2, "Ball2");

		ball.resetBall();

		ball2.resetBall();

		Score score = new Score(camera.getBounds());

		Game.gameObjectHandler.addGameObject(score, "Score");
	}

}