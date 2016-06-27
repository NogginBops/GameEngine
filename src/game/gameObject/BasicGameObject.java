package game.gameObject;

import java.awt.geom.Rectangle2D;

import game.debug.DebugOutputProvider;

/**
 * 
 * @version 1.0
 * @author Julius Häger
 */
public class BasicGameObject implements GameObject, DebugOutputProvider {
	
	//JAVADOC: BasicGameObject

	/**
	 * 
	 */
	protected float x;
	/**
	 * 
	 */
	protected float y;
	
	//TODO: Should width and height be integer or float? (Probably a float)
	
	/**
	 * 
	 */
	protected float width;
	/**
	 * 
	 */
	protected float height;
	
	/**
	 * 
	 */
	protected Rectangle2D.Float bounds;
	
	/**
	 * The current Z-order of the GameObject
	 */
	protected int zOrder;
	
	private boolean active = true;

	
	//NOTE: Should maybe not take the zOrder as a param
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 */
	public BasicGameObject(float x, float y, float width, float height, int zOrder) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle2D.Float(x, y, width, height);
		this.zOrder = zOrder;
	}
	
	/**
	 * @param bounds
	 * @param zOrder
	 */
	public BasicGameObject(Rectangle2D.Float bounds, int zOrder) {
		this.x = bounds.x;
		this.y = bounds.y;
		this.width = bounds.width;
		this.height = bounds.height;
		this.bounds = bounds;
		this.zOrder = zOrder;
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
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public Rectangle2D.Float getBounds() {
		return bounds;
	}

	@Override
	public void updateBounds() {
		bounds.x = (int) x;
		bounds.y = (int) y;
		bounds.width = width;
		bounds.height = height;
	}
	
	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
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

	@Override
	public String[] getDebugValues() {
		return new String[]{
				"<b>Active: </b>" + active,
				"<b>X: </b>" + x,
				"<b>Y:</b> " + y,
				"<b>Width: </b>" + width,
				"<b>Height: </b>" + height,
				"<b>ZOrder: </b>" + zOrder
		};
	}
}
