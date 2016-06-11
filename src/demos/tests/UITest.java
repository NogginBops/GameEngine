package demos.tests;

import java.awt.Color;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.Game;
import game.GameInitializer;
import game.GameSettings;
import game.IO.IOHandler;
import game.IO.load.LoadRequest;
import game.UI.UI;
import game.UI.border.Border;
import game.UI.border.SolidBorder;
import game.UI.elements.containers.BasicUIContainer;
import game.UI.elements.image.UIImage;
import game.UI.elements.input.UIButton;
import game.UI.elements.text.UILabel;
import game.gameObject.graphics.Camera;

/**
 * @author Julius Häger
 *
 */
public class UITest implements GameInitializer {

	//JAVADOC: UITest
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameSettings settings = GameSettings.getDefaultGameSettings();
		
		settings.putSetting("Name", "UITest #1");
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("GameInit", new UITest());
		
		Game game = new Game(settings);
		
		game.run();
		
	}
	
	//NOTE: Button seems to be broken
	
	@Override
	public void initialize(Game game, GameSettings settings) {
		UI hud = new UI(new Rectangle2D.Float(200, 100, 400, 400));

		BasicUIContainer container = new BasicUIContainer(200, 300);
		Border border = new SolidBorder(20, Color.MAGENTA);
		container.setBorder(border);
		hud.addUIElement(container);

		BasicUIContainer container2 = new BasicUIContainer(100, 100);
		Border border2 = new SolidBorder(10, Color.CYAN);
		container2.setBorder(border2);
		container.addUIElement(container2);

		UILabel lable = new UILabel("Test label");
		lable.setColor(Color.WHITE);
		container2.addUIElement(lable);
		
		Image image;
		try {
			image = IOHandler.load(new LoadRequest<BufferedImage>("Image", new File("./res/Background.png"), BufferedImage.class, "DefaultPNGLoader")).result;
		} catch (IOException e) {
			image = null;
		}
		
		UIImage UIimg = new UIImage(0, 0, 40, 100, image);
		UIimg.setNativeSize();
		UIimg.setZOrder(2);
		container.addUIElement(UIimg);
		
		UIButton button = new UIButton(40, 40, 100, 40);
		Game.gameObjectHandler.addGameObject(button);
		button.setZOrder(10);
		container.addUIElement(button);
		
		Game.gameObjectHandler.addGameObject(hud);
	}

}
