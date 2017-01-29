package game;

import game.util.IDHandler;

/**
 * @author Julius Häger
 *
 */
public abstract class GameSystem {
	
	//TODO: Figure out what it means to be a GameSystem
	
	private static IDHandler<GameSystem> gameSystems = new IDHandler<>();
	
	private String name;
	
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
}
