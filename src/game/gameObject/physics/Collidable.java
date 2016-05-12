package game.gameObject.physics;

import java.awt.geom.Area;

/**
 * 
 * @version 1.0
 * @author Julius H�ger
 */
public interface Collidable extends Movable {

	// JAVADOC: Collidable
	
	//TODO: Custom collision shape 
	
	/**
	 * 
	 * @return
	 */
	public Area getCollitionArea();

	/**
	 * This method is called when this object has collided with another
	 * {@link Collidable} object.
	 * 
	 * @param collisionObject
	 *            the {@link Collidable} this object has collided with.
	 */
	public void hasCollided(Collidable collisionObject);
}
