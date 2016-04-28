package game.gameObject;

import java.awt.Rectangle;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class BasicGameObject implements GameObject {
	
	//JAVADOC: BasicGameObject

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
	protected Rectangle bounds;
	
	/**
	 * The current Z-order of the GameObject
	 */
	protected int zOrder;

	
	//NOTE: Should maybe not take the zOrder as a param
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 */
	public BasicGameObject(float x, float y, int width, int height, int zOrder) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle((int)x, (int)y, width, height);
		this.zOrder = zOrder;
	}
	
	/**
	 * @param bounds
	 * @param zOrder
	 */
	public BasicGameObject(Rectangle bounds, int zOrder) {
		this.x = bounds.x;
		this.y = bounds.y;
		this.width = bounds.width;
		this.height = bounds.height;
		this.bounds = bounds;
		this.zOrder = zOrder;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void updateBounds() {
		bounds = new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public int getZOrder() {
		return zOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		if (zOrder == object.getZOrder()) {
			return 0;
		} else {
			return zOrder > object.getZOrder() ? 1 : -1;
		}
	}
}
