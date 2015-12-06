package game.gameObject.physics;

import java.awt.Rectangle;

import game.gameObject.GameObject;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public interface Collidable extends Movable {
	
	//JAVADOC: Collidable
	
	@Override
	public Rectangle getBounds();
	
	@Override
	public void updateBounds();
	
	@Override
	public int getZOrder();
	
	@Override
	public int compareTo(GameObject object);
	
	@Override
	public void update(long timeMillis);
	
	/**
	 * 
	 * @return
	 */
	public int getMass();
	
	/**
	 * This method is called when the next {@link #update(long)} method call will cause this object to collide with another {@link Collidable} object.
	 * 
	 * @param collisionObject the {@link Collidable} this object will collide with.
	 */
	public void willCollide(Collidable collisionObject);
	
	/**
	 * This method is called when this object has collided with another {@link Collidable} object.
	 * 
	 * @param collisionObject the {@link Collidable} this object has collided with.
	 */
	public void hasCollided(Collidable collisionObject);
	
	/**
	 * This method is called when the next {@link #update(long)} method call will cause this object to exit a collision.
	 * 
	 * @param collidedObject the {@link Collidable} this object will stop colliding with
	 */
	public void willNoLongerCollide(Collidable collidedObject);
	
	/**
	 * This method is called when this object exits a collision.
	 * 
	 * @param collidedObject the {@link Collidable} this object is noLongerColliding with.
	 */
	public void noLongerColliding(Collidable collidedObject);
}
