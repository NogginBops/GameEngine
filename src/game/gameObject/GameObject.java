package game.gameObject;

import java.awt.Rectangle;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface GameObject extends Comparable<GameObject> {
	
	// JAVADOC: GameObject

	/**
	 * Returns the GameObject bounds. Used to determine if the GameObjects
	 * methods should be called or not.
	 * 
	 * @return the GameObject bounds
	 */
	public Rectangle getBounds();

	/**
	 * <p>
	 * Updates the bounds of the GameObject
	 * </p>
	 * 
	 * <p>
	 * The bounds are used to determine if the GameObjects methods are called.
	 * </p>
	 */
	public void updateBounds();

	/**
	 * Returns the current Z-Order of the GameObject.
	 * 
	 * @return the current Z-Order of the GameObject.
	 */
	public int getZOrder();

	/**
	 * Used to compare the Z-Order values of two GameObjects.
	 */
	@Override
	public int compareTo(GameObject object);

}
