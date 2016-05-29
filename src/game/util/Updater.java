package game.util;

import java.util.concurrent.CopyOnWriteArrayList;

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

	//TODO: Merge with Game.java
	
	protected CopyOnWriteArrayList<UpdateListener> listeners;

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
	 * @param timeNano
	 *            time since last update (in milliseconds)
	 */
	protected void propagateUpdate(float deltaTime) {
		for (UpdateListener listener : listeners) {
			if(listener.isActive()){
				listener.update(deltaTime);
			}
		}
	}
}
