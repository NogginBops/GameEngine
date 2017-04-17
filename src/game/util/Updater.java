package game.util;

import java.util.concurrent.CopyOnWriteArrayList;

import game.GameSystem;

/**
 * <p>
 * A class used to handle updates and propagate the updates to listening
 * UpdateListeners.
 * </p>
 * <p>
 * <i> It's up to the subclass to handle timing</i>
 * </p>
 * 
 * @version 1.0
 * @author Julius Häger
 */
public abstract class Updater {

	// JAVADOC: Updater
	
	protected CopyOnWriteArrayList<UpdateListener> listeners;
	
	protected IDHandler<GameSystem> systems = GameSystem.getIDHandler();
	
	/**
	 * 
	 */
	public Updater() {
		listeners = new CopyOnWriteArrayList<UpdateListener>();
	}

	/**
	 * Called to propagate a update call to all {@link UpdateListener
	 * UpdateListeners} in the protected {@link #listeners} list.
	 * 
	 * @param deltaTime
	 */
	public void propagateUpdate(float deltaTime) {
		for (ID<GameSystem> gameSystem : systems) {
			if (gameSystem.object.getEnabled()) {
				gameSystem.object.earlyUpdate(deltaTime);
			}
		}
		
		for (UpdateListener listener : listeners) {
			if(listener.isActive()){
				listener.update(deltaTime);
			}
		}
		
		for (ID<GameSystem> gameSystem : systems) {
			if (gameSystem.object.getEnabled()) {				
				gameSystem.object.lateUpdate(deltaTime);
			}
		}
	}
}
