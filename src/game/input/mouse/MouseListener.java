package game.input.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import game.gameObject.GameObject;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface MouseListener {

	// JAVADOC: MouseListener
	
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
	
	/**
	 * Returns the MouseListener bounds. Used to determine if the MouseListener
	 * methods should be called or not.
	 * 
	 * @return the MouseListeners bounds
	 */
	public Rectangle2D getBounds();
	
	/**
	 * <p>Returns whether or not the MouseListener is active.</p>
	 * 
	 * @return Whether or not the MouseListener is active.
	 */
	public boolean isActive();
	
	/**
	 * <p>Sets the GameObjects active state.</p>
	 * <p>The active state determines if some subsystems should include the GameObject. 
	 * Notable examples are painting and collision detection</p>
	 * 
	 * @param active
	 */
	public void setActive(boolean active);
	
	/**
	 * Returns the current Z-Order of the MouseListener.
	 * 
	 * @return the current Z-Order of the MouseListener.
	 */
	public int getZOrder();
}
