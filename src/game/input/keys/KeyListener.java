package game.input.keys;

import java.awt.event.KeyEvent;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface KeyListener {

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
	
	/**
	 * @return
	 */
	public boolean isActive();

}
