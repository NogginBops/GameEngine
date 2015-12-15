package game.UI;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import game.gameObject.GameObject;

/**
 * 
 * 
 * @author Julius Häger
 */
public abstract class UIElement {
	
	//TODO: UIElement
	
	// JAVADOC: UIElement

	/**
	 * @return
	 */
	public abstract Rectangle getBounds();

	/**
	 * 
	 */
	public abstract void updateBounds();

	/**
	 * @return
	 */
	public abstract int getZOrder();

	/**
	 * @param object
	 * @return
	 */
	public abstract int compareTo(GameObject object);

	/**
	 * @param g2d
	 */
	public abstract void paint(Graphics2D g2d);
}
