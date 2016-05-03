package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;

import game.gameObject.graphics.Camera;
import game.screen.ScreenRect;

public class GameSettings {
	
	public static GameSettings DEFAULT = getDefaultGameSettings();
	
	private static GameSettings getDefaultGameSettings() {
		GameSettings defaultSettigns = new GameSettings();
		
		defaultSettigns.putSetting("Name", "Game");
		
		defaultSettigns.putSetting("ScreenMode", false);
		
		Dimension res = new Dimension(800, 600);
		
		defaultSettigns.putSetting("Resolution", res);
		
		defaultSettigns.putSetting("MainCamera", new Camera(new Rectangle(res), ScreenRect.FULL, new Color(0.15f, 0.15f, 0.15f, 1f)));
		
		defaultSettigns.putSetting("OnScreenDebug", false);
		
		defaultSettigns.putSetting("Debug", false);
		
		defaultSettigns.putSetting("GameInit", new GameInitializer() {
			
			@Override
			public void initialize(Game game, GameSettings settings) {
				
				//TODO: Make it so that you can access things in Game better.
				Game.log.logWarning("Default Game!");
				
			}
			
		});
		
		
		
		return defaultSettigns;
	}
	
	private HashMap<String, Object> settings;
	
	public GameSettings() {
		settings = new HashMap<String, Object>();
	}
	
	public Object getSetting(String name){
		return settings.get(name);
	}
	
	public <T> T getSettingAs(String name, Class<T> clazz){
		Object obj = settings.get(name);
		if(clazz.isAssignableFrom(obj.getClass())){
			return clazz.cast(obj);
		}
		return null;
	}
	
	public boolean containsSetting(String name){
		return settings.containsKey(name);
	}
	
	public void putSetting(String name, Object value){
		settings.put(name, value);
	}
}
