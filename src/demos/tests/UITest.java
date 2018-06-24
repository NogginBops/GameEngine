package demos.tests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;

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
		GameSettings settings = GameSettings.createDefaultGameSettings();
		
		settings.putSetting("Name", "UITest #1");
		
		settings.getSettingAs("MainCamera", Camera.class).receiveKeyboardInput(true);

		settings.putSetting("UseDefaultKeyBindings", true);
		
		settings.putSetting("OnScreenDebug", true);
		
		settings.putSetting("DebugID", false);
		
		settings.putSetting("GameInit", new UITest());
		
		Game.setup(settings);
		
		Game.run();
		
	}
	
	//NOTE: Button seems to be broken
	
	@Override
	public void initialize(GameSettings settings) {
		BasicUIContainer container = new BasicUIContainer(200, 300);
		Border border = new SolidBorder(20, Color.MAGENTA);
		container.setBorder(border);
		
		BasicUIContainer container2 = new BasicUIContainer(100, 100);
		Border border2 = new SolidBorder(10, Color.CYAN);
		container2.setBorder(border2);
		container.addChild(container2);

		UILabel lable = new UILabel("Test label");
		lable.setColor(Color.WHITE);
		container2.addChild(lable);
		
		BufferedImage image = IOHandler.load(new LoadRequest<BufferedImage>("Image", Paths.get("./res/Background.png"), BufferedImage.class, "DefaultPNGLoader")).result;
		
		UIImage UIimg = new UIImage(0, 0, 40, 100, image);
		UIimg.setNativeSize();
		UIimg.setZOrder(2);
		container.addChild(UIimg);
		
		UIButton button = new UIButton(20, 20, 100, 40);
		Game.mouseHandler.addMouseListener(button);
		button.setZOrder(10);
		container.addChild(button);
		
		button.addActionListener((e) -> {
			System.out.println("asdf");
			System.out.println(e);
		});
		
		UI hud = new UI(0, 0, 0, container);
		
		Game.gameObjectHandler.addGameObject(hud);
	}

}
