package game.gameObject.graphics;

import java.awt.Rectangle;

import game.gameObject.BasicGameObject;
import game.gameObject.GameObject;
import game.gameObject.physics.Movable;
import game.util.UpdateListener;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public abstract class Sprite extends BasicGameObject implements Paintable, Movable, UpdateListener {

	// JAVADOC: Sprite

	/**
	 * The dynamic-x (The movement in the x-axis measured in pixels/second)
	 */
	protected float dx;
	/**
	 * The dynamic-x (The movement in the y-axis measured in pixels/second)
	 */
	protected float dy;

	/**
	 * 
	 * 
	 * @param x
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param y
	 *            the start x-coordinate of the upper most corner of the Sprite
	 * @param width
	 *            the width of the Sprite (in pixels)
	 * @param height
	 *            the height of the Sprite (in pixels)
	 */
	public Sprite(float x, float y, int width, int height) {
		super(x, y, width, height, 5);
	}
	
	//TODO: Add sorting layers for sprites and such

	/**
	 * @param bounds
	 */
	public Sprite(Rectangle bounds) {
		super(bounds, 5);
	}

	@Override
	public int getZOrder() {
		return zOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		return zOrder - object.getZOrder();
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * <p>
	 * Updates the bounds of the Sprite.
	 * </p>
	 * 
	 * <p>
	 * The bounds are used to determine if the object is drawn.
	 * </p>
	 */
	@Override
	public void updateBounds() {
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public void update(long timeNano) {
		x += (dx * timeNano) / 1000000000;
		y += (dy * timeNano) / 1000000000;
		updateBounds();
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Sets the location of the sprite.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public void setLocation(float x, float y){
		this.x = x;
		this.y = y;
	}

	@Override
	public float getDX() {
		return dx;
	}

	@Override
	public float getDY() {
		return dy;
	}

	@Override
	public void setDX(float dx) {
		this.dx = dx;
	}

	@Override
	public void setDY(float dy) {
		this.dy = dy;
	}
}
