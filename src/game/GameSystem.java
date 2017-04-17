package game;

import game.debug.DebugOutputProvider;
import game.util.IDHandler;

/**
 * @author Julius Häger
 *
 */
public abstract class GameSystem implements DebugOutputProvider {
	
	//TODO: Figure out what it means to be a GameSystem
	
	private static IDHandler<GameSystem> gameSystems = new IDHandler<>();
	
	private String name;
	
	private boolean enabled = true;
	
	/**
	 * @param name The name of the GameSystem
	 */
	public GameSystem(String name) {
		this.name = name;
		
		gameSystems.addObject(this, name);
	}
	
	/**
	 * Called by a Updater before updating all the UpdateListeners.
	 * 
	 * @param deltaTime
	 */
	public abstract void earlyUpdate(float deltaTime);
	
	/**
	 * Called by a Updater after updating all the UpdateListeners.
	 * 
	 * @param deltaTime
	 */
	public abstract void lateUpdate(float deltaTime);
	
	/**
	 * Returns the name of this GameSystem.
	 * 
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getEnabled(){
		return enabled;
	}
	/**
	 * 
	 * @param enabled
	 */
	protected void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}	
	
	/**
	 * 
	 */
	protected void destroy(){
		gameSystems.removeObject(this);
		setEnabled(false);
	}
	
	/**
	 * @return
	 */
	public static IDHandler<GameSystem> getIDHandler(){
		return gameSystems;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public static GameSystem getGameSystem(String name){
		return gameSystems.getObject(name);
	}
	
	@Override
	public String[] getDebugValues() {
		return new String[]{
				"<b>Name: </b> " + name,
				"<b>Enabled: </b>" + enabled,
		};
	}
}
