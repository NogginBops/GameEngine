package demos.pong;

import java.awt.event.KeyEvent;

import demos.pong.Pad.Side;
import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.UI.UI;
import game.gameObject.graphics.Camera;

/**
 * @author Julius Häger
 *
 */
public class Pong implements GameInitializer {
	
	//JAVADOC: Pong
	
	private static Camera camera;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "Pong");
		
		camera = settings.getSettingAs("MainCamera", Camera.class);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("GameInit", new Pong());
		
		Game.setup(settings);
		
		Game.run();
		
	}

	@Override
	public void initialize(GameSettings settings) {

		Pad rightPad = new Pad(camera.getWidth() - 60, 40, 10, 50, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
				camera.getBounds(), Side.RIGHT);

		Game.gameObjectHandler.addGameObject(rightPad, "RightPad");

		Pad leftPad = new Pad(50, 40, 10, 50, KeyEvent.VK_W, KeyEvent.VK_S, camera.getBounds(), Side.LEFT);

		Game.gameObjectHandler.addGameObject(leftPad, "LeftPad");
		
		Ball ball = new Ball(Game.screen.getWidth() / 2 - 8, Game.screen.getHeight() / 2 - 8, 16, 16,
				camera.getBounds());

		Game.gameObjectHandler.addGameObject(ball, "Ball");

		ball.resetBall();

		Score score = new Score(camera.getBounds());

		Game.keyHandler.addListener(score);
		
		UI ui = new UI(0, 0, 0, score);
		
		Game.gameObjectHandler.addGameObject(ui, "Score");
	}

}
