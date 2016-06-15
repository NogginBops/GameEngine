package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import game.UI.UI;
import game.UI.elements.containers.BasicUIContainer;
import game.UI.elements.text.UILabel;
import game.gameObject.graphics.Camera;
import game.screen.ScreenManager;
import game.screen.ScreenRect;

/**
 * @author Julius Häger
 *
 */
public class GameSettings {
	
	//JAVADOC: GameSettings
	
	/**
	 * @return
	 */
	public static GameSettings createDefaultGameSettings() {
		
		GameSettings defaultSettigns = new GameSettings();
		
		defaultSettigns.putSetting("Name", "Game");
		
		defaultSettigns.putSetting("ScreenMode", ScreenManager.NORMAL);
		
		Dimension res = new Dimension(800, 600);
		
		//NOTE: This is probably also going to be replaced
		defaultSettigns.putSetting("Resolution", res);
		
		//TODO: Should camera be a setting or should it be in game init? Probably game init so that it works when switching scenes
		defaultSettigns.putSetting("MainCamera", new Camera(new Rectangle2D.Float(0, 0, res.width, res.height), ScreenRect.FULL, new Color(0.15f, 0.15f, 0.15f, 1f)));
		
		defaultSettigns.putSetting("OnScreenDebug", false);
		
		defaultSettigns.putSetting("DebugLog", false);
		
		defaultSettigns.putSetting("DebugID", false);
		
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
				
				UI ui = new UI(new Rectangle2D.Float(0, 0, 800, 600), outerContainer);
		
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
