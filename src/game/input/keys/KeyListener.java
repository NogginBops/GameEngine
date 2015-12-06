package game.input.keys;

import java.awt.event.KeyEvent;

import game.gameObject.GameObject;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface KeyListener extends GameObject {
	
	// JAVADOC: KeyListener
	
	/**
	 * 
	 * @param e
	 */
	public void keyTyped(KeyEvent e);
	
	/**
	 * 
	 * @param e
	 */
	public void keyPressed(KeyEvent e);
	
	/**
	 * 
	 * @param e
	 */
	public void keyReleased(KeyEvent e);
	
	/**
	 * 
	 * @return
	 */
	public boolean shouldReceiveKeyboardInput();

}
