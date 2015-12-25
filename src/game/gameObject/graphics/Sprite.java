package game.gameObject.graphics;

import java.awt.Rectangle;

import game.gameObject.GameObject;
import game.gameObject.physics.Movable;
import game.util.UpdateListener;

/**
 * 
 * 
 * @version 1.0
 * @author Julius Häger
 */
public abstract class Sprite implements Paintable, Movable, UpdateListener {

	// JAVADOC: Sprite

	/**
	 * 
	 */
	protected float x;
	/**
	 * 
	 */
	protected float y;

	/**
	 * 
	 */
	protected int width;
	/**
	 * 
	 */
	protected int height;

	/**
	 * 
	 */
	protected float dx;
	/**
	 * 
	 */
	protected float dy;

	/**
	 * 
	 */
	protected Rectangle bounds;

	/**
	 * The current Z-order of the Sprite
	 */
	protected int zOrder = 5;

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
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	/**
	 * @param bounds
	 */
	public Sprite(Rectangle bounds) {
		this.x = bounds.x;
		this.y = bounds.y;
		this.width = bounds.width;
		this.height = bounds.height;
		this.bounds = bounds;
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
