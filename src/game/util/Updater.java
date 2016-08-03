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
	
	protected CopyOnWriteArrayList<UpdateListener> listeners;
	
	protected CopyOnWriteArrayList<UpdateProcedure> earlyProcedures;
	
	protected CopyOnWriteArrayList<UpdateProcedure> lateProcedures;

	/**
	 * 
	 */
	public Updater() {
		listeners = new CopyOnWriteArrayList<UpdateListener>();
		earlyProcedures = new CopyOnWriteArrayList<UpdateProcedure>();
		lateProcedures = new CopyOnWriteArrayList<UpdateProcedure>();
	}

	/**
	 * Called to propagate a update call to all {@link UpdateListener
	 * UpdateListeners} in the protected {@link #listeners} list.
	 * 
	 * @param deltaTime
	 */
	public void propagateUpdate(float deltaTime) {
		for (UpdateProcedure updateProcedure : earlyProcedures) {
			updateProcedure.proc(deltaTime);
		}
		for (UpdateListener listener : listeners) {
			if(listener.isActive()){
				listener.update(deltaTime);
			}
		}
		for (UpdateProcedure updateProcedure : lateProcedures) {
			updateProcedure.proc(deltaTime);
		}
	}
	
	public void addEarlyUpdateProcedure(UpdateProcedure proc){
		earlyProcedures.add(proc);
	}
	
	public void removeEarlyUpdateProcedure(UpdateProcedure proc) {
		earlyProcedures.remove(proc);
	}
	
	public void addLateUpdateProcedure(UpdateProcedure proc){
		lateProcedures.add(proc);
	}
	
	public void removeLateUpdateProcedure(UpdateProcedure proc) {
		lateProcedures.remove(proc);
	}
}
