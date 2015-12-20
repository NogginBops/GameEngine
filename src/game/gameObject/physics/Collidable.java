package game.gameObject.physics;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface Collidable extends Movable {

	// JAVADOC: Collidable

	/**
	 * This method is called when this object has collided with another
	 * {@link Collidable} object.
	 * 
	 * @param collisionObject
	 *            the {@link Collidable} this object has collided with.
	 */
	public void hasCollided(Collidable collisionObject);
}
