package demos.tests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.gameObject.graphics.Camera;
import game.gameObject.graphics.Sprite;
import game.screen.Screen;

/**
 * @author Julius Häger
 *
 */
public class TextureTest implements GameInitializer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "TextureTest");
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("ScreenMode", Screen.Mode.NORMAL);
		
		settings.putSetting("GameInit", new TextureTest());
		
		Game game = new Game(settings);
		
		game.run();
		
	}
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		
		BufferedImage sheetImage = null;
		
		sheetImage = IOHandler.load(new LoadRequest<BufferedImage>("TextureSheet", Paths.get("./res/verticalScroller/graphics/ShipsSheet.png"), BufferedImage.class, "DefaultPNGLoader")).result;
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);;

		settings.putSetting("UseDefaultKeyBindings", true);
		
		Sprite s = new Sprite(100, 100, 2f, sheetImage);
		
		s.setColor(Color.yellow);
		
		Game.gameObjectHandler.addGameObject(s, "Test sprite");
		
	}
}
