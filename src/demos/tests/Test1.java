package demos.tests;

import java.awt.Color;
import java.util.Random;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.gameObject.graphics.Camera;
import game.test.OtherPaintable;
import game.test.TestInputSprite;
import game.test.TestSprite;

/**
 * @author Julius Häger
 *
 */
public class Test1 implements GameInitializer {

	//JAVADOC: Test1
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "Test #1");
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("GameInit", new Test1());
		
		Game game = new Game(settings);
		
		game.run();
		
	}
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		OtherPaintable z1 = new OtherPaintable(0, 0, 100, 100, 1, Color.BLUE);

		OtherPaintable z2 = new OtherPaintable(10, 10, 100, 100, 2, Color.RED);

		OtherPaintable z3 = new OtherPaintable(20, 20, 100, 100, 3, Color.GREEN);

		Game.gameObjectHandler.addGameObject(z2, "OtherPaintable2");

		Game.gameObjectHandler.addGameObject(z3, "OtherPaintable3");

		Game.gameObjectHandler.addGameObject(z1, "OtherPaintable1");

		TestSprite t = new TestSprite(50, 50, 100, 70);

		Game.gameObjectHandler.addGameObject(t, "TestSprite1");

		t.setDX(30);
		t.setDY(3);

		TestSprite t2 = new TestSprite(400, 50, 20, 20);

		Game.gameObjectHandler.addGameObject(t2, "TestSprite2");

		t2.setDX(-60);
		t2.setDY(-1);

		TestInputSprite testInput = new TestInputSprite(100, 100, 100, 100, 10, false);

		Game.gameObjectHandler.addGameObject(testInput, "TestInputSprite1");

		TestInputSprite testInput2 = new TestInputSprite(200, 150, 100, 100, 9, false);

		Game.gameObjectHandler.addGameObject(testInput2, "TestInuptSprite2");

		Random rand = new Random();

		for (int x = 0; x < 30; x++) {
			for (int y = 0; y < 30; y++) {
				TestSprite test = new TestSprite(x * 30, y * 30, 20, 20);
				Game.gameObjectHandler.addGameObject(test);
				
				test.setDX(rand.nextFloat() * 50);
				test.setDY(rand.nextFloat() * 50);
			}
		}
	}
}
