package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.function.Consumer;

import game.UI.UI;
import game.UI.elements.containers.BasicUIContainer;
import game.UI.elements.text.UILabel;
import game.gameObject.graphics.Camera;
import game.input.KeyInputHandler;
import game.screen.Screen;
import game.screen.ScreenRect;

/**
 * @author Julius Häger
 *
 */
public class GameSettings {
	
	//JAVADOC: GameSettings
	
	//NOTE: Should the game settigns be statically available for easier access
	//This would allow for batter use of the settigns system for things like,
	//resource paths and other variables that are shared by the different components of the game
	
	/**
	 * @return
	 */
	public static GameSettings createDefaultGameSettings() {
		
		GameSettings defaultSettigns = new GameSettings();
		
		defaultSettigns.putSetting("Name", "Game");
		
		defaultSettigns.putSetting("ScreenMode", Screen.Mode.NORMAL);
		
		Dimension res = new Dimension(800, 600);
		
		//NOTE: This is probably also going to be replaced
		defaultSettigns.putSetting("Resolution", res);
		
		defaultSettigns.putSetting("TargetFPS", 60);
		
		//NOTE: Should the default 'TargetUPS' be 1000 or 500. This requires testing on different machines to see if 1000 results in a OK CPU load.
		defaultSettigns.putSetting("TargetUPS", 500);
		
		//TODO: Should camera be a setting or should it be in game init? Probably game init so that it works when switching scenes
		defaultSettigns.putSetting("MainCamera", new Camera(new Rectangle2D.Float(0, 0, res.width, res.height), ScreenRect.FULL, new Color(0.15f, 0.15f, 0.15f, 1f)));
		
		defaultSettigns.putSetting("KeyBindings", new Consumer<KeyInputHandler>() {
			@Override
			public void accept(KeyInputHandler t) {
				t.addKeyBinding("Up", KeyEvent.VK_UP, KeyEvent.VK_W);
				t.addKeyBinding("Down", KeyEvent.VK_DOWN, KeyEvent.VK_S);
				t.addKeyBinding("Right", KeyEvent.VK_RIGHT, KeyEvent.VK_D);
				t.addKeyBinding("Left", KeyEvent.VK_LEFT, KeyEvent.VK_A);
			}
		});
		
		defaultSettigns.putSetting("OnScreenDebug", false);
		
		defaultSettigns.putSetting("DebugLog", false);
		
		defaultSettigns.putSetting("DebugID", false);
		
		defaultSettigns.putSetting("DebugGameSystem", false);
		
		//NOTE: Should this happen in settings or should there ba another way to handle it
		defaultSettigns.putSetting("GameInit", new GameInitializer() {
			
			//TODO: This screen should display a nicer error log or similar
			//The way it is now it's unacceptably ugly and serves no purpose.
			
			@Override
			public void initialize(Game game, GameSettings settings) {
				
				//TODO: Make it so that you can access things in Game better.
				Game.log.logWarning("Default Game!");
				
				UILabel lable = new UILabel(15, 5, "NO GAME!!!");
				
				lable.setColor(Color.red);
				
				lable.setFont(new Font("Monospaced", Font.PLAIN, 18));
				
				BasicUIContainer innerContainer = new BasicUIContainer(300, 250, 200, 100, lable);
				
				innerContainer.setBorderColor(Color.red.darker().darker());
				
				innerContainer.setBorderSize(30);
				
				BasicUIContainer outerContainer = new BasicUIContainer(0, 0, 800, 600, innerContainer);
				
				outerContainer.setBorderColor(Color.red.darker());
				
				outerContainer.setBorderSize(2);
				
				UI ui = new UI(0, 0, 0, outerContainer);
		
				Game.gameObjectHandler.addGameObject(ui, "Demo UI");
				
			}
			
		});
		
		return defaultSettigns;
	}

	private HashMap<String, Object> settings;
	
	/**
	 * 
	 */
	public GameSettings() {
		settings = new HashMap<String, Object>();
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Object getSetting(String name){
		return settings.get(name);
	}
	
	/**
	 * @param name
	 * @param clazz
	 * @return
	 */
	public <T> T getSettingAs(String name, Class<T> clazz){
		Object obj = settings.get(name);
		if(clazz.isAssignableFrom(obj.getClass())){
			return clazz.cast(obj);
		}
		return null;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public boolean containsSetting(String name){
		return settings.containsKey(name);
	}
	
	/**
	 * @param name
	 * @param value
	 */
	public void putSetting(String name, Object value){
		settings.put(name, value);
	}
}
