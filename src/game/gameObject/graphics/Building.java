package game.gameObject.graphics;

import game.gameObject.GameObject;

import java.awt.Rectangle;

/**
 * @author Julius Häger
 *
 */
public abstract class Building implements Paintable {

	// TODO: Building: Remove

	protected int x;
	protected int y;

	protected int width;
	protected int height;

	/**
	 * The bounds of the Building. (Used for painting)
	 */
	protected Rectangle bounds;

	/**
	 * The Z-Order of the building (Default = 1)
	 */
	protected int ZOrder = 1;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Building(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle(x, y, width, height);
	}

	@Override
	public int getZOrder() {
		return ZOrder;
	}

	@Override
	public int compareTo(GameObject object) {
		if (ZOrder == object.getZOrder()) {
			return 0;
		} else {
			return ZOrder > object.getZOrder() ? 1 : -1;
		}
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void updateBounds() {

	}
}
