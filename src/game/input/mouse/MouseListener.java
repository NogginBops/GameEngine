package game.input.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import game.gameObject.GameObject;

/**
 * 
 * @version 1.0
 * @author Julius H�ger
 */
public interface MouseListener extends GameObject {

	// JAVADOC: MouseListener
	
<<<<<<< HEAD
	/**
	 * 
	 */
	@Override
	public int getZOrder();

=======
>>>>>>> origin/GameEngine(Nightly)
	/**
	 * 
	 * 
	 * @param e
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * 
	 * @param e
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * 
	 * @param e
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * 
	 * @param e
	 */
	public void mouseEntered(MouseEvent e);

	/**
	 * 
	 * @param e
	 */
	public void mouseExited(MouseEvent e);

	/**
	 * 
	 * @param e
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * 
	 * @param e
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * 
	 * @param e
	 */
	public void mouseWheelMoved(MouseWheelEvent e);

	/**
	 * This function is called to determine if the input should be absorbed by
	 * this {@link GameObject} or if it should be passed down to
	 * {@link GameObject GameObjects} covered by this one.
	 * 
	 * @return whether or not the input should be absorbed or passed down to
	 *         {@link GameObject GameObjects} located under this one.
	 */
	public boolean absorb();

	/**
	 * 
	 * @return
	 */
	public boolean souldReceiveMouseInput();

}
