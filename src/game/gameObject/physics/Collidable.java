package game.gameObject.physics;

import java.awt.Shape;

import game.gameObject.GameObject;

/**
 * 
 * The collidable interface describes a {@link Movable} {@link GameObject} that should be collision checked.
 * The collision check is performed by the {@link PhysicsEngine} game object subsystem.
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface Collidable extends Movable {

	//TODO: Bounding box checking?
	
	/**
	 * This methods returns the accurate shape that the collision object has.
	 * Used by the {@link PhysicsEngine} to check for collisions.
	 * 
	 * @return the <code>Shape</code> of the collidable
	 */
	default public Shape getCollitionShape(){
		return getTranformedShape();
	}

	/**
	 * This method is called when this object has collided with another
	 * {@link Collidable} object.
	 * 
	 * @param collisionObject the {@link Collidable} this object has collided with.
	 */
	public void hasCollided(Collidable collisionObject);
}
