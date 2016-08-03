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
	
	protected CopyOnWriteArrayList<GameSystem> systems;
<<<<<<< HEAD
	
=======

>>>>>>> refs/remotes/origin/GameEngine(Nightly)
	/**
	 * 
	 */
	public Updater() {
		listeners = new CopyOnWriteArrayList<UpdateListener>();
		
		systems = new CopyOnWriteArrayList<GameSystem>();
	}

	/**
	 * Called to propagate a update call to all {@link UpdateListener
	 * UpdateListeners} in the protected {@link #listeners} list.
	 * 
	 * @param deltaTime
	 */
	public void propagateUpdate(float deltaTime) {
		for (GameSystem gameSystem : systems) {
			gameSystem.earlyUpdate(deltaTime);
		}
		
		for (UpdateListener listener : listeners) {
			if(listener.isActive()){
				listener.update(deltaTime);
			}
		}
		
		for (GameSystem gameSystem : systems) {
			gameSystem.lateUpdate(deltaTime);
		}
	}
	
	/**
	 * @param system
	 */
	public void addSystem(GameSystem system){
		systems.add(system);
	}
	
	/**
	 * @param system
	 */
	public void removeSystem(GameSystem system) {
		systems.remove(system);
	}
}
