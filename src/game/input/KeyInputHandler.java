package game.input;

import game.input.keys.KeyListener;
import game.util.GameObjectHandler;

import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class KeyInputHandler {
	
	// JAVADOC: KeyInputHandler
	
	private KeyListener selectedListener;
	
	private GameObjectHandler gameObjectHandeler;
	
	private CopyOnWriteArrayList<KeyListener> listeners = new CopyOnWriteArrayList<KeyListener>();
	
	/**
	 * 
	 * @param gameObjectHandeler
	 */
	public KeyInputHandler(GameObjectHandler gameObjectHandeler) {
		this.gameObjectHandeler = gameObjectHandeler;
	}
	
	/**
	 * 
	 * 
	 * @param listener
	 */
	public void selectListener(KeyListener listener){
		selectedListener = listener;
	}
	
	/**
	 * 
	 * 
	 * @param e
	 */
	public void keyTyped(KeyEvent e) {
		if(selectedListener != null){
			selectedListener.keyTyped(e);
		}
		if(gameObjectHandeler.haveObjectsChanged()){
			listeners = gameObjectHandeler.getAllGameObjectsExtending(KeyListener.class);
		}
		for(KeyListener listener : listeners){
			if(listener.shouldReceiveKeyboardInput()){
				listener.keyTyped(e);
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @param e
	 */
	public void keyPressed(KeyEvent e) {
		if(selectedListener != null){
			selectedListener.keyPressed(e);
		}
		if(gameObjectHandeler.haveObjectsChanged()){
			listeners = gameObjectHandeler.getAllGameObjectsExtending(KeyListener.class);
		}
		for(KeyListener listener : listeners){
			if(listener.shouldReceiveKeyboardInput()){
				listener.keyPressed(e);
			}
		}
	}

	/**
	 * 
	 * 
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		if(selectedListener != null){
			selectedListener.keyReleased(e);
		}
		if(gameObjectHandeler.haveObjectsChanged()){
			listeners = gameObjectHandeler.getAllGameObjectsExtending(KeyListener.class);
		}
		for(KeyListener listener : listeners){
			if(listener.shouldReceiveKeyboardInput()){
				listener.keyReleased(e);
			}
		}
	}
}
