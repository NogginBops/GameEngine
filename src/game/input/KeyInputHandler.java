package game.input;

import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

import game.Game;
import game.input.keys.KeyListener;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class KeyInputHandler {

	// JAVADOC: KeyInputHandler

	private KeyListener selectedListener;

	private CopyOnWriteArrayList<KeyListener> listeners = new CopyOnWriteArrayList<KeyListener>();

	/**
	 * 
	 */
	public KeyInputHandler() {
		
	}

	/**
	 * 
	 * 
	 * @param listener
	 */
	public void selectListener(KeyListener listener) {
		selectedListener = listener;
	}
	
	/**
	 * 
	 */
	public void updateListeners(){
		listeners = Game.gameObjectHandler.getAllGameObjectsExtending(KeyListener.class);
	}

	/**
	 * 
	 * 
	 * @param e
	 */
	public void keyTyped(KeyEvent e) {
		if (selectedListener != null) {
			selectedListener.keyTyped(e);
		}
		for (KeyListener listener : listeners) {
			if (listener.isActive() && listener.shouldReceiveKeyboardInput()) {
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
		if (selectedListener != null) {
			selectedListener.keyPressed(e);
		}
		for (KeyListener listener : listeners) {
			if (listener.isActive() && listener.shouldReceiveKeyboardInput()) {
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
		if (selectedListener != null) {
			selectedListener.keyReleased(e);
		}
		for (KeyListener listener : listeners) {
			if (listener.isActive() && listener.shouldReceiveKeyboardInput()) {
				listener.keyReleased(e);
			}
		}
	}
}
