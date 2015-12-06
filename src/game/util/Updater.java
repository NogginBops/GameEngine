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

	private CopyOnWriteArrayList<UpdateListener> listeners;

	/**
	 * 
	 */
	public Updater() {
		listeners = new CopyOnWriteArrayList<UpdateListener>();
	}

	/**
	 * 
	 * @param listener
	 *            the listener to add
	 */
	public void addUpdateListener(UpdateListener listener) {
		listeners.add(listener);
	}

	/**
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void removeListener(UpdateListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Called to propagate a update call to all registered {@link UpdateListener UpdateListeners}.
	 * 
	 * @param timeNano
	 *            time since last update (in milliseconds)
	 */
	protected void propogateUpdate(long timeNano) {
		for (UpdateListener listener : listeners) {
			listener.update(timeNano);
		}
	}
}
