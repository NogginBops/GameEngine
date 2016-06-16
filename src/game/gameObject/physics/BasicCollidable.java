package game.gameObject.physics;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * @author Julius Häger
 *
 */
public class BasicCollidable extends BasicMovable implements Collidable{
	
	protected Shape shape;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param zOrder
	 * @param shape 
	 */
	public BasicCollidable(float x, float y, float width, float height, int zOrder, Shape shape) {
		super(x, y, width, height, zOrder);
		
		this.shape = shape;
	}
	
	/**
	 * @param rect
	 * @param zOrder
	 * @param shape 
	 */
	public BasicCollidable(Rectangle2D.Float rect, int zOrder, Shape shape) {
		super(rect, zOrder);
		
		this.shape = shape;
	}

	@Override
	public Shape getCollitionShape() {
		return shape;
	}

	@Override
	public void hasCollided(Collidable collisionObject) {
		
	}
}
