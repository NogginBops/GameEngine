package game.gameObject.physics;

import game.util.UpdateListener;

/**
 * The movable interface is implemented to provide movement related functions.
 * <br>
 * Movement is controlled by a timer witch calls the update function and
 * provides the time that passed in between the last update and the current one.
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface Movable extends UpdateListener {

	/**
	 * Returns the current x value of the Movable
	 * 
	 * @return the x value of the Movable
	 */
	public float getX();

	/**
	 * Returns the current y value of the Movable
	 * 
	 * @return the y value of the Movable
	 */
	public float getY();

	/**
	 * Sets the current x value of the Movable
	 * 
	 * @param x
	 *            the new x value
	 */
	public void setX(float x);

	/**
	 * Sets the current y value of the Movable
	 * 
	 * @param y
	 *            the new y value
	 */
	public void setY(float y);
	
	/**
	 * Sets the current x and y value of the Movable
	 * @param x	
	 * 	the new x value
	 * @param y
	 * 	the new y value
	 */
	public void setPosition(float x, float y);

	/**
	 * Returns the current dynamic x (X-axis movement) of the movable. <br>
	 * Dynamic x is measured in pixels per second.
	 * 
	 * @return the current dynamic x (X-axis movement)
	 */
	public float getDX();

	/**
	 * Returns the current dynamic y (Y-axis movement) of the movable. <br>
	 * Dynamic y is measured in pixels per second.
	 * 
	 * @return the current dynamic y (Y-axis movement)
	 */
	public float getDY();

	/**
	 * Used to set the dynamic x (X-axis movement) of the movable. <br>
	 * Dynamic x is measured in pixels per second.
	 * 
	 * @param dx
	 */
	public void setDX(float dx);

	/**
	 * Used to set the dynamic y (Y-axis movement) of the movable. <br>
	 * Dynamic y is measured in pixels per second.
	 * 
	 * @param dy
	 */
	public void setDY(float dy);
	
	//TODO: Make movement be updated by the PhysicsEngine (RigidBody interface?)

	/**
	 * <p>
	 * Used to update the movement based on the time passed since the last
	 * update.
	 * </p>
	 * <p>Divide by 1000000000 for seconds.</p>
	 * <p>
	 * <b>Note:</b> <br>
	 * Should be implemented so that the Dynamic x and y equal to pixels per
	 * second.
	 * @param timeNano 
	 * time since last update (in nanoseconds)
	 */
	@Override
	public void update(long timeNano);

}
