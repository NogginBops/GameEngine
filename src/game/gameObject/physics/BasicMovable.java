package game.gameObject.physics;

import java.awt.Shape;

import game.gameObject.BasicGameObject;
import game.util.math.vector.Vector2D;

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
	 * @param shape 
	 * @param zOrder 
	 */
	public BasicMovable(Shape shape, int zOrder) {
		super(shape, zOrder);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 */
	public BasicMovable(float x, float y, float width, float height, int zOrder) {
		super(x, y, width, height, zOrder);
	}
	
	/**
	 * @param x 
	 * @param y 
	 * @param shape
	 * @param zOrder
	 */
	public BasicMovable(float x, float y, Shape shape, int zOrder) {
		super(x, y, shape, zOrder);
	}

	@Override
	public float getX() {
		return transform.getX();
	}

	@Override
	public float getY() {
		return transform.getY();
	}

	@Override
	public Vector2D getPosition() {
		return transform.getPosition();
	}
	
	@Override
	public void setX(float x) {
		transform.setX(x);
	}

	@Override
	public void setY(float y) {
		transform.setY(y);
	}
	
	@Override
	public void setPosition(float x, float y) {
		transform.setPosition(x, y);
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
	public Vector2D getVelocity(){
		return new Vector2D(dx, dy);
	}
	
	@Override
	public void setDX(float dx) {
		this.dx = dx;
	}
	
	@Override
	public void setDY(float dy) {
		this.dy = dy;
	}
	
	@Override
	public void setVelocity(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * @param vel
	 */
	@Override
	public void setVelocity(Vector2D vel){
		this.dx = vel.x;
		this.dy = vel.y;
	}

	@Override
	public void update(float deltaTime) {
		transform.translate(dx * deltaTime, dy * deltaTime);
	}
	
	@Override
	public String[] getDebugValues() {
		String[] newValues = new String[]{
				"<b>DX:</b> " + dx,
				"<b>DY:</b> " + dy
		};
		String[] oldValues = super.getDebugValues();
		
		String[] mergedValues = new String[oldValues.length + newValues.length];
		System.arraycopy(oldValues, 0, mergedValues, 0, oldValues.length);
		System.arraycopy(newValues, 0, mergedValues, oldValues.length, newValues.length);
		
		return mergedValues;
	}
}
