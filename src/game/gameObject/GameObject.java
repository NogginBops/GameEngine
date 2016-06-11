package game.gameObject;

import java.awt.geom.Rectangle2D;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface GameObject extends Comparable<GameObject> {

	// JAVADOC: GameObject
	
	/**
	 * @return
	 */
	public float getX();
	
	/**
	 * @return
	 */
	public float getY();
	
	/**
	 * @return
	 */
	public float getWidth();
	
	/**
	 * @return
	 */
	public float getHeight();
	
	/**
	 * Returns the GameObject bounds. Used to determine if the GameObjects
	 * methods should be called or not.
	 * 
	 * @return the GameObject bounds
	 */
	public Rectangle2D.Float getBounds();

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
	 * <p>Returns whether or not the GameObject is active.</p>
	 * <p>This determines if some subsystems should include the GameObject. 
	 * Notable examples are painting and collision detection</p>
	 * 
	 * @return Whether or not the GameObject is active.
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
