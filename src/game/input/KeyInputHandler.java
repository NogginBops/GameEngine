package game.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import game.Game;
import game.gameObject.handler.event.GameObjectCreatedEvent;
import game.gameObject.handler.event.GameObjectDestroyedEvent;
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
	
	private HashMap<String, CopyOnWriteArrayList<Integer>> keyBindings;

	/**
	 * 
	 */
	public KeyInputHandler() {
		keyBindings = new HashMap<String, CopyOnWriteArrayList<Integer>>();
		
		//FIXME: Due to KeyListener no longer extending GameObject it's no longer guaranteed that it will be registered.
		
		Game.eventMachine.addEventListener(GameObjectCreatedEvent.class, (event) -> {
			if(event.object instanceof KeyListener){
				addListener((KeyListener) event.object);
			}});
		
		Game.eventMachine.addEventListener(GameObjectDestroyedEvent.class, (event) -> {
			if(event.object instanceof KeyListener){
				removeListener((KeyListener) event.object);
			}});
	}
	
	/**
	 * @param name
	 * @param keycodes
	 */
	public void addKeyBinding(String name, Integer ... keycodes){
		if(keyBindings.containsKey(name)){
			for (Integer keycode : keycodes) {
				keyBindings.get(name).add(keycode);
			}
		}else{
			keyBindings.put(name, new CopyOnWriteArrayList<Integer>(keycodes));
		}
	}
	
	/**
	 * @param name
	 */
	public void removeBinding(String name){
		if(keyBindings.containsKey(name)){
			keyBindings.remove(name);
		}
	}
	
	/**
	 * @param name
	 * @param keycode
	 */
	public void removeBinding(String name, int keycode){
		if(keyBindings.containsKey(name)){
			keyBindings.get(name).remove(keycode);
		}
	}
	
	//TODO: Better name
	
	/**
	 * @param name
	 * @param keycode
	 * @return
	 */
	public boolean isBound(String name, int keycode){
		if(keyBindings.containsKey(name)){
			return keyBindings.get(name).contains(keycode);
		}else{
			Game.log.logWarning("Checked non-registered keybinding: " + name, "Input", "KeyBinding");
			return false;
		}
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
	 * @param listener
	 */
	public void addListener(KeyListener listener){
		listeners.add(listener);
	}
	
	/**
	 * @param listener
	 */
	public void removeListener(KeyListener listener){
		listeners.remove(listener);
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
