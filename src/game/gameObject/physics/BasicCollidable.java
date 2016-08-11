package game.gameObject.physics;

import java.awt.Shape;

/**
 * @author Julius Häger
 *
 */
public class BasicCollidable extends BasicMovable implements Collidable{
	
	//JAVADOC: BasicCollidable
	
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
	 * @param x 
	 * @param y 
	 * @param rect
	 * @param zOrder
	 * @param shape 
	 */
	public BasicCollidable(float x, float y, Shape shape, int zOrder) {
		super(x, y, shape, zOrder);
	}

	@Override
	public void hasCollided(Collidable collisionObject) {
		
	}
}
