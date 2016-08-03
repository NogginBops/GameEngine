package game;

/**
 * @author Julius Häger
 *
 */
public abstract class GameSystem {
	
	//TODO: Figure out what it means to be a GameSystem
	
	private String name;
	
	/**
	 * @param name The name of the GameSystem
	 */
	public GameSystem(String name) {
		this.name = name;
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
}
