package game.gameObject.physics;

import java.awt.Shape;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface Collidable extends Movable {

	// JAVADOC: Collidable
	
	//TODO: Custom collision shape 
	
	/**
	 * 
	 * @return
	 */
	public Shape getCollitionShape();

	/**
	 * This method is called when this object has collided with another
	 * {@link Collidable} object.
	 * 
	 * @param collisionObject
	 *            the {@link Collidable} this object has collided with.
	 */
	public void hasCollided(Collidable collisionObject);
}
