package game.gameObject;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import game.debug.DebugOutputProvider;
import game.gameObject.transform.Transform;

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
	protected Transform<GameObject> transform = new Transform<GameObject>(this);
	
	/**
	 * 
	 */
	protected Shape shape;
	
	/**
	 * The current Z-order of the GameObject
	 */
	protected int zOrder;
	
	private boolean active = true;
	
	/**
	 * @param transform
	 * @param shape
	 * @param zOrder
	 */
	public BasicGameObject(Transform<GameObject> transform, Shape shape, int zOrder) {
		this.transform = transform;
		this.shape = shape;
		this.zOrder = zOrder;
	}
	
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
		transform.setPosition(x, y);
		shape = new Rectangle2D.Float(0, 0, width, height);
		this.zOrder = zOrder;
	}
	
	/**
	 * @param x 
	 * @param y 
	 * @param shape 
	 * @param zOrder 
	 */
	public BasicGameObject(float x, float y, Shape shape, int zOrder) {
		transform.setPosition(x, y);
		this.shape = shape;
		this.zOrder = zOrder;
	}

	@Override
	public Transform<GameObject> getTransform() {
		return transform;
	}
	
	@Override
	public void setTransform(Transform<GameObject> transform) {
		this.transform = transform;
	}
	
	@Override
	public Shape getShape() {
		return shape;
	}
	
	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public Shape getTranformedShape() {
		return transform.getAffineTransform().createTransformedShape(shape);
	}
	
	@Override
	public Rectangle2D getBounds() {
		// NOTE: There might be a faster way to do this by manually calculating the values. It might be hard.
		return transform.getAffineTransform().createTransformedShape(shape).getBounds2D();
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
	public String[] getDebugValues() {
		return new String[]{
				"<b>Active: </b>" + active,
				"<b>X: </b>" + transform.getX(),
				"<b>Y:</b> " + transform.getY(),
				"<b>Width: </b>" + getBounds().getWidth(),
				"<b>Height: </b>" + getBounds().getHeight(),
				"<b>ZOrder: </b>" + zOrder
		};
	}
}
