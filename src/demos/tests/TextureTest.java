package demos.tests;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.gameObject.graphics.MappedSpriteSheet;
import game.gameObject.graphics.Sprite;

public class TextureTest implements GameInitializer {

	public static void main(String[] args) {
		
		GameSettings settings = GameSettings.getDefaultGameSettings();
		
		settings.putSetting("Name", "TextureTest");
		
		settings.putSetting("GameInit", new TextureTest());
		
		Game game = new Game(settings);
		
		game.run();
		
	}
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		
		BufferedImage sheetImage = null;
		
		try {
			sheetImage = IOHandler.load(new LoadRequest<BufferedImage>("TextureSheet", new File("./res/verticalScroller/graphics/ShipsSheet.png"), BufferedImage.class, "DefaultPNGLoader")).result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MappedSpriteSheet mappedSpritesheet = new MappedSpriteSheet(sheetImage, new Color(191, 220, 191));
		
		mappedSpritesheet.addMapping("Sprite1", new Rectangle(0, 0, 80, 80));
		
		Sprite sprite1 = new Sprite(10, 10, 2, mappedSpritesheet.getSprite("Sprite1"));
		
		Game.gameObjectHandler.addGameObject(sprite1, "Test");
		
	}
}
