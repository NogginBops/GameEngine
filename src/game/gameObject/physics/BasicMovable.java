package game.gameObject.physics;

import java.awt.Rectangle;

import game.gameObject.BasicGameObject;

/**
 * @author Julius Häger
 *
 */
public class BasicMovable extends BasicGameObject implements Movable {
	
	//JAVADOC: BasicMovable
	
	/**
	 * The dynamic-x (The movement in the x-axis measured in pixels/second)
	 */
	protected float dx;
	
	/**
	 * The dynamic-x (The movement in the y-axis measured in pixels/second)
	 */
	protected float dy;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 */
	public BasicMovable(float x, float y, int width, int height, int zOrder) {
		super(x, y, width, height, zOrder);
	}
	
	/**
	 * @param rect
	 * @param zOrder
	 */
	public BasicMovable(Rectangle rect, int zOrder) {
		super(rect, zOrder);
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
		updateBounds();
	}

	@Override
	public void setY(float y) {
		this.y = y;
		updateBounds();
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		updateBounds();
	}

	@Override
	public float getDX() {
		return dx;
	}

	@Override
	public void setDX(float dx) {
		this.dx = dx;
	}
	
	@Override
	public float getDY() {
		return dy;
	}

	@Override
	public void setDY(float dy) {
		this.dy = dy;
	}

	@Override
	public void update(long timeNano) {
		x += (dx * timeNano) / 1000000000;
		y += (dy * timeNano) / 1000000000;
		updateBounds();
	}
}
